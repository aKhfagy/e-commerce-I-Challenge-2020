package com.example.e_commerce;

import java.util.ArrayList;

public class ShoppingCart {
    private int numberOfItems;
    public ArrayList<Item> itemsList;

    ShoppingCart(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
        this.numberOfItems = 0;
    }

    void addToShoppingCart(Item selectedItem) {
        itemsList.add(selectedItem);
        numberOfItems++;
    }

    void removeFromShoppingCart(Item selectedItem) {
        itemsList.remove(selectedItem);
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<Item> itemsList) {
        this.itemsList = itemsList;
    }

}
