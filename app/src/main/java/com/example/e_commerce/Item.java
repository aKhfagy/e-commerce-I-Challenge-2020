package com.example.e_commerce;

public class Item {
    private final String name;
    private final String cost;
    private final String size;
    private int quantity ;

    public Item(String name, String cost, String size) {
        this.name = name;
        this.cost = cost;
        this.size = size;
        this.quantity = 1;
    }

    public String getName() {
        return this.name;
    }

    public String getCost() {
        return this.cost;
    }

    public String getSize() {
        return this.size;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void incrementQuantity(){
        quantity++;
    }

    public void decrementQuantity(){
        if (quantity > 1)
        quantity--;
    }
}
