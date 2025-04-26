package com.qrfoodorder.restaurant.controller;

import com.qrfoodorder.restaurant.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/QR")
public class QRController{
    @Autowired
    private QRService qrService;

    @PostMapping("/addTable")
    public ResponseEntity<String> addNewTable(@RequestBody String restId) throws ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(qrService.addTable(restId));
    }

    @GetMapping("/getTableCount")
    public ResponseEntity<Integer> getTableCount(@RequestBody String restId) throws ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.FOUND).body(qrService.getTableCount(restId));
    }

    @GetMapping("/getQRScannedCount")
    public ResponseEntity<Integer> getQRScannedCount(@RequestBody String restId) throws ExecutionException, InterruptedException {
        return ResponseEntity.status(HttpStatus.OK).body(qrService.getQRScannedCount(restId));
    }
}
