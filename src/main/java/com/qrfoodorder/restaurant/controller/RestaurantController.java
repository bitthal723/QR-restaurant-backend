package com.qrfoodorder.restaurant.controller;

import com.qrfoodorder.restaurant.model.MenuItem;
import com.qrfoodorder.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/addMenu")
    public ResponseEntity<String> addMenuItem(@RequestHeader("Authorization") String token, @RequestBody List<MenuItem> menuList) throws ExecutionException, InterruptedException {
       return ResponseEntity.ok(restaurantService.addMenuItems(token, menuList));
    }

    @GetMapping("/getMenu")
    public ResponseEntity<List<MenuItem>> getMenuItem(@RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(restaurantService.getMenuItem(token));
    }

    @DeleteMapping("/deleteMenu")
    public ResponseEntity<String> deleteMenu(@RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(restaurantService.deleteMenu(token));
    }

    @DeleteMapping("/deleteById/{itemId}")
    public ResponseEntity<String> deleteMenuItemById(@RequestHeader("Authorization") String token, @PathVariable int itemId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(restaurantService.deleteMenuItemById(token, itemId));
    }

}
