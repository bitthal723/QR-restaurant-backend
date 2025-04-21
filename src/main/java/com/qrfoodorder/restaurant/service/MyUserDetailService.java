package com.qrfoodorder.restaurant.service;

import com.qrfoodorder.restaurant.firebase_service.FireStoreUserService;
import com.qrfoodorder.restaurant.model.UserCredentials;
import com.qrfoodorder.restaurant.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials user;
        FireStoreUserService userService = applicationContext.getBean(FireStoreUserService.class);
        try {
            user = userService.getUser(username);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(user != null){
            return new UserPrincipal(user);
        }
        System.out.println("2 at user detail ");
        return null;
    }
}
