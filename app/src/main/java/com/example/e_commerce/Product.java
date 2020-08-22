package com.example.e_commerce;

import android.database.Cursor;

import java.util.ArrayList;

public class Product {
    private final ArrayList<Item> items;
    private final ArrayList<Item> chosenItems;

    public Product() {
        this.items = new ArrayList<>();
        this.chosenItems = new ArrayList();
    }

    public void load(Cursor cursor) {
        while(cursor.moveToNext()) {
            items.add(new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Item> getChosenItems() {
        return chosenItems;
    }
}
