package com.qrfoodorder.restaurant.firebase_service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.qrfoodorder.restaurant.model.MenuItem;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@Service
public class FireStoreRestaurantService {
    private final Firestore db = FirestoreClient.getFirestore();

    public String addMenuItemsFireStore(String restaurantId, List<MenuItem> menuList) throws ExecutionException, InterruptedException {
        List<MenuItem> existingMenuList = getMenuItemFromFireStore(restaurantId);
        int existingMenuListSize = existingMenuList.size();
        if(existingMenuListSize == 0){
            for(int i = 0; i < menuList.size(); i++){
                menuList.get(i).setItemId(i + 1);
            }
        }else{
            int maxItemId = 0;
            for(int i = 0;i<existingMenuListSize;i++){
                int itemId = existingMenuList.get(i).getItemId();
                if(maxItemId < itemId){
                    maxItemId = itemId;
                }
            }
            for(int i = 0; i<menuList.size(); i++){
                menuList.get(i).setItemId(i + maxItemId + 1);
            }
        }
        db.collection("restaurants").document(restaurantId).update("menu", FieldValue.arrayUnion(menuList.toArray()));
        return "Items are added to menu";
    }
    public List<MenuItem> getMenuItemFromFireStore(String restaurantId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = db.collection("restaurants")
                .document(restaurantId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot queryDocumentSnapshots = future.get();
        List<HashMap<String, Object>> getMapList = (List<HashMap<String, Object>>) queryDocumentSnapshots.get("menu");
        if(getMapList != null){
            List<MenuItem> menuItemList = new ArrayList<>();
            for(int i = 0; i < getMapList.size(); i++){
                MenuItem tempMenuItem = new MenuItem();
                tempMenuItem.setItemId(Integer.parseInt(getMapList.get(i).get("itemId").toString()));
                tempMenuItem.setItemName(getMapList.get(i).get("itemName").toString());
                tempMenuItem.setItemPrice(Integer.parseInt(getMapList.get(i).get("itemPrice").toString()));
                menuItemList.add(tempMenuItem);
            }
            return menuItemList;
        }
        return new ArrayList<>();
    }
    public void deleteMenuFromFireStore(String restaurantId){
        db.collection("restaurants").document(restaurantId).update("menu", FieldValue.delete());
    }
    public String deleteMenuItemByIdFromFireStore(String restaurantId, int itemId) throws ExecutionException, InterruptedException {
        List<MenuItem> menuList = getMenuItemFromFireStore(restaurantId);
        int getItemId = -1;
        for(int i = 0;i<menuList.size();i++){
            if(menuList.get(i).getItemId() == itemId) {
                getItemId = i;
            }
        }
        if(getItemId != -1){
            menuList.remove(getItemId);
            deleteMenuFromFireStore(restaurantId);
            db.collection("restaurants").document(restaurantId).update("menu", FieldValue.arrayUnion(menuList.toArray()));
            return "Item Deleted with item id "+itemId;
        }
        return "Item not found";
    }
}
