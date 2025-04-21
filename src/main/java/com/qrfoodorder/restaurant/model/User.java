package com.qrfoodorder.restaurant.model;

import org.springframework.stereotype.Component;

@Component
public class User {
    private String email;
    private String password;
    private String restaurantId;
    private String restaurantName;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String rest_id) {
        this.restaurantId = rest_id;
    }
}
