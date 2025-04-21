package com.qrfoodorder.restaurant.service;

import com.qrfoodorder.restaurant.firebase_service.FireStoreRestaurantService;
import com.qrfoodorder.restaurant.firebase_service.FireStoreUserService;
import com.qrfoodorder.restaurant.model.MenuItem;
import com.qrfoodorder.restaurant.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RestaurantService {
    @Autowired
    private FireStoreRestaurantService service;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private FireStoreUserService fireStoreUserService;
    public String addMenuItems(String token, List<MenuItem> menuItemList) throws ExecutionException, InterruptedException {
        String email = jwtUtils.extractSubject(token.substring(7));
        String restaurantId = fireStoreUserService.getRestaurantId(email);
       return service.addMenuItemsFireStore(restaurantId, menuItemList);
    }

    public List<MenuItem> getMenuItem(String token) throws ExecutionException, InterruptedException {
        String email = jwtUtils.extractSubject(token.substring(7));
        String restaurantId = fireStoreUserService.getRestaurantId(email);
        try {
            return service.getMenuItemFromFireStore(restaurantId);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteMenu(String token) throws ExecutionException, InterruptedException {
        String email = jwtUtils.extractSubject(token.substring(7));
        String restaurantId = fireStoreUserService.getRestaurantId(email);
        service.deleteMenuFromFireStore(restaurantId);
        return "Menu deleted!";
    }

    public String deleteMenuItemById(String token, int itemId) throws ExecutionException, InterruptedException {
        String email = jwtUtils.extractSubject(token.substring(7));
        String restaurantId = fireStoreUserService.getRestaurantId(email);
        try {
            return service.deleteMenuItemByIdFromFireStore(restaurantId, itemId);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
