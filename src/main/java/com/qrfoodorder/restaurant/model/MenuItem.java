package com.qrfoodorder.restaurant.model;

public class MenuItem {
    private int itemId;
    private String itemName;
    private int itemPrice;

    public int getItemId() { return itemId; }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
}
