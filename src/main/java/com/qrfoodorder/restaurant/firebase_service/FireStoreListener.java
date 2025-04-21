package com.qrfoodorder.restaurant.firebase_service;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Service
public class FireStoreListener {
    public void listenToSpecificOrder(String restID) {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("orders").document("YQUK25RFND")
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        System.err.println("Listen failed: " + e);
                        return;
                    }
                    if (snapshot != null && snapshot.exists()) {
                        System.out.println("Order updated: " + snapshot.getData());
                    } else {
                        System.out.println("Order does not exist!");
                    }
                });
    }
    @PostConstruct
    public void init() {
        listenToSpecificOrder("orderId_123");
    }
}