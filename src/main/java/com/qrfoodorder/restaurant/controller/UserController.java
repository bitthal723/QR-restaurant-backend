package com.qrfoodorder.restaurant.controller;

import com.qrfoodorder.restaurant.model.User;
import com.qrfoodorder.restaurant.model.UserCredentials;
import com.qrfoodorder.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("/user")
public class  UserController {
    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody User user) {
       return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody UserCredentials userCredentials) {
        System.out.println(userCredentials.getEmail());
        return ResponseEntity.ok(userService.signIn(userCredentials));
    }

    @PostMapping("/getRestId")
    public ResponseEntity<String> getRestId(@RequestBody UserCredentials userCredentials) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(userService.getRestaurantId(userCredentials.getEmail()));
    }
}
