package com.example.e_commerce;

public class Item {
    private final String name;
    private final String cost;
    private final String size;

    public Item(String name, String cost, String size) {
        this.name = name;
        this.cost = cost;
        this.size = size;
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
}
