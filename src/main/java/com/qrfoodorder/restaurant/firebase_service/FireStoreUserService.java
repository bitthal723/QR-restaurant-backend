package com.qrfoodorder.restaurant.firebase_service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.qrfoodorder.restaurant.model.MenuItem;
import com.qrfoodorder.restaurant.model.User;
import com.qrfoodorder.restaurant.model.UserCredentials;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class FireStoreUserService {
    private final Firestore db = FirestoreClient.getFirestore();

    public String createUser(User user) throws ExecutionException, InterruptedException {
        if(!checkUserExist(user.getEmail())) {
            String generatedRestaurantId = generateRandom();
            while(checkRestaurantId(generatedRestaurantId)){
                generatedRestaurantId = generateRandom();
            }
            user.setRestaurantId(generatedRestaurantId);
            DocumentReference documentReference = db.collection("email").document(user.getEmail());
            documentReference.set(user);
            setRestaurantCollection(generatedRestaurantId,user.getRestaurantName(),user.getEmail());
            setOrderCollection(generatedRestaurantId);
            return "User created successfully.";
        }else{
           return "Email is already registered!";
        }
    }
    private Boolean checkUserExist(String email) {
        CollectionReference collectionReference = db.collection("email");
        ApiFuture<QuerySnapshot> future = collectionReference.get();
        //        QuerySnapshot querySnapshot = future.get();
        List<String> emailList = new ArrayList<>();
        try{
            //    List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            for(DocumentSnapshot documentSnapshot: future.get().getDocuments()){
                emailList.add(documentSnapshot.getId());
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return emailList.contains(email);
    }
    public UserCredentials getUser(String email) throws ExecutionException, InterruptedException {
        if(checkUserExist(email)) {

            DocumentReference documentReference = db.collection("email").document(email);
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot documentSnapshot = future.get();
            UserCredentials user = new UserCredentials();
            user.setEmail(documentSnapshot.getId());
            user.setPassword(String.valueOf(documentSnapshot.get("password")));
            return user;
        }
        return null;
    }


    private Boolean checkRestaurantId(String generatedRestId) throws ExecutionException, InterruptedException {
        List<String> restaurantId = getRestaurantIdList();
        return restaurantId.contains(generatedRestId);
    }
    private List<String> getRestaurantIdList() throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = db.collection("restaurants");
        ApiFuture<QuerySnapshot> apiFuture = collectionReference.get();
        QuerySnapshot queryDocumentSnapshots = apiFuture.get();
        List<String> documentIdList = new ArrayList<>();
        for(QueryDocumentSnapshot q:queryDocumentSnapshots){
            documentIdList.add(q.getId());
        }
        return documentIdList;
    }

    public String getRestaurantId(String email) throws ExecutionException, InterruptedException {
        if(checkUserExist(email)){
            DocumentReference documentReference = db.collection("email").document(email);
            ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
            DocumentSnapshot documentSnapshot = apiFuture.get();
            return String.valueOf(documentSnapshot.get("restaurantId"));
        }
        return "No restaurant ID found for "+email;
    }

    private static String generateRandom() {
        String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand=new Random();
        StringBuilder res=new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randIndex=rand.nextInt(aToZ.length());
            res.append(aToZ.charAt(randIndex));
        }
        return res.toString();
    }
    private void setRestaurantCollection(String restaurantId, String restaurantName, String email){
        DocumentReference documentReference = db.collection("restaurants").document(restaurantId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("email",email);
        map.put("rest_name",restaurantName);
        map.put("menu",new ArrayList<MenuItem>());
        map.put("total_qr_generated", 0);
        map.put("total_qr_scanned", 0);
        documentReference.set(map);
    }
    private void setOrderCollection(String restaurantId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("items",new ArrayList<>());
        db.collection("orders").document(restaurantId).set(map);
    }
}
