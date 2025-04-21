package com.qrfoodorder.restaurant.controller;

import com.qrfoodorder.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping("/get")
    public ResponseEntity<List<List<Map<String, Object>>>> getOrder(@RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
       return ResponseEntity.ok(service.getOrder(token));
    }
    @PostMapping("/remove/{tableNumber}")
    public ResponseEntity<String> removeOrder(@RequestHeader("Authorization") String token, @PathVariable int tableNumber) throws ExecutionException, InterruptedException {
       return ResponseEntity.ok(service.removeOrder(token, tableNumber));
    }
}
