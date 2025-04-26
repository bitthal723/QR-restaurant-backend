package com.qrfoodorder.restaurant.service;

import com.qrfoodorder.restaurant.firebase_service.FireStoreQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class QRService {
    @Autowired
    private FireStoreQRService fireStoreQRService;
    public String addTable(String restId) throws ExecutionException, InterruptedException {
        return fireStoreQRService.addTable(restId);
    }

    public int getTableCount(String restId) throws ExecutionException, InterruptedException {
        return fireStoreQRService.getTableCount(restId);
    }

    public int getQRScannedCount(String restId) throws ExecutionException, InterruptedException {
        return fireStoreQRService.getQRScannedCount(restId);
    }
}
