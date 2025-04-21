package com.qrfoodorder.restaurant.service;

import com.qrfoodorder.restaurant.firebase_service.FireStoreOrderService;
import com.qrfoodorder.restaurant.firebase_service.FireStoreRestaurantService;
import com.qrfoodorder.restaurant.firebase_service.FireStoreUserService;
import com.qrfoodorder.restaurant.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {
    @Autowired
    private FireStoreOrderService service;
    @Autowired
    private FireStoreUserService userService;
    @Autowired
    private JWTUtils jwtUtils;
    public List<List<Map<String, Object>>> getOrder(String token) throws ExecutionException, InterruptedException {
        String email =  jwtUtils.extractSubject(token.substring(7));
        String restId = userService.getRestaurantId(email);
        try {
            return service.getOrderFromFireStore(restId);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String removeOrder(String token, int tableNumber) throws ExecutionException, InterruptedException {
        String email = jwtUtils.extractSubject(token.substring(7));
        String restId = userService.getRestaurantId(email);
        try {
            return service.removeOrderFromFireStore(restId, tableNumber);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
