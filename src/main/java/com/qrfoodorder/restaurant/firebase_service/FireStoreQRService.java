package com.qrfoodorder.restaurant.firebase_service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FireStoreQRService {
    private final Firestore db = FirestoreClient.getFirestore();

    public String addTable(String restId) throws ExecutionException, InterruptedException {
        int previousCount = getTableCount(restId);
        DocumentReference documentReference = db.collection("restaurants").document(restId);
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("total_qr_generated", previousCount+1);
        documentReference.update("total_qr_generated", previousCount+1);
        return "Success";
    }
    public int getTableCount(String restId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = db.collection("restaurants").document(restId);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        return Integer.parseInt(String.valueOf(documentSnapshot.get("total_qr_generated")));
    }

    public int getQRScannedCount(String restId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = db.collection("restaurants").document(restId);
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();
        return Integer.parseInt(String.valueOf(documentSnapshot.get("total_qr_scanned")));
    }
}
