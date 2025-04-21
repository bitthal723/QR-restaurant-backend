package com.qrfoodorder.restaurant.service;


import com.qrfoodorder.restaurant.firebase_service.FireStoreUserService;
import com.qrfoodorder.restaurant.model.User;
import com.qrfoodorder.restaurant.model.UserCredentials;
import com.qrfoodorder.restaurant.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    @Autowired
    private FireStoreUserService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtils jwtUtils;

    public String createUser(User user){
        try {
           return service.createUser(user);
        } catch (ExecutionException | InterruptedException e) {
            return e.toString();
        }

    }

    public String signIn(UserCredentials userCredentials) {
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtUtils.generateToken(userCredentials.getEmail());
        }
        return "Incorrect Username or Password";
    }

    public String getRestaurantId(String email) throws ExecutionException, InterruptedException {
        return service.getRestaurantId(email);
    }
}
