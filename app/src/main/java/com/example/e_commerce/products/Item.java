package com.example.e_commerce.products;

public class Item {
    private final String name;
    private final String cost;
    private final String path;
    private int quantity ;

    public Item(String name, String cost, String path) {
        this.name = name;
        this.cost = cost;
        this.path = path;
        this.quantity = 1;
    }

    public String getName() {
        return this.name;
    }

    public String getCost() {
        return this.cost;
    }

    public String getPath() {
        return this.path;
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
