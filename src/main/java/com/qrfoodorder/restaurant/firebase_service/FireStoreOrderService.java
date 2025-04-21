package com.qrfoodorder.restaurant.firebase_service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class FireStoreOrderService {
    private final Firestore db = FirestoreClient.getFirestore();

    public List<List<Map<String, Object>>> getOrderFromFireStore(String restId) throws ExecutionException, InterruptedException {

        DocumentReference documentReference = db.collection("orders")
                .document(restId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot queryDocumentSnapshots = future.get();

        List<Map<String, Object>> orderItemList = (List<Map<String, Object>>) queryDocumentSnapshots.get("items");
        List<Integer> temp = new ArrayList<>();

        if(orderItemList != null){
            for(Map<String, Object> objectMap : orderItemList){
                int num = Integer.parseInt(objectMap.get("tableNumber").toString());
                if(!temp.contains(num)) temp.add(num);
            }
            Collections.sort(temp);
            List<List<Map<String, Object>>> list = new ArrayList<>();
            for(Integer num : temp){
                List<Map<String, Object>> sameOrderList = new ArrayList<>();
                for(Map<String, Object> objectMap : orderItemList){
                    int tempTableNum =  Integer.parseInt(objectMap.get("tableNumber").toString());
                    if(num == tempTableNum){
                        sameOrderList.add(objectMap);
                    }
                }
            list.add(sameOrderList);
            }
            return list;
        }
        return new ArrayList<>();
    }
    private void removeEveryOrder(String restId){
        db.collection("orders").document(restId).update("items", FieldValue.delete());
    }
    public String removeOrderFromFireStore(String restId, int tableNumber) throws ExecutionException, InterruptedException {
        List<List<Map<String, Object>>> list = getOrderFromFireStore(restId);
        DocumentReference documentReference = db.collection("orders").document(restId);
        boolean flag = false;
        for (List<Map<String, Object>> maps : list) {
            if ( Integer.parseInt(maps.get(0).get("tableNumber").toString()) == tableNumber) {
                for (Map<String, Object> map : maps){
                    documentReference.update("items", FieldValue.arrayRemove(map));
                    flag = true;
                }
            }
        }
        if(flag) return "order removed at table number "+tableNumber;
        return "No order at table number "+tableNumber;
    }
}
