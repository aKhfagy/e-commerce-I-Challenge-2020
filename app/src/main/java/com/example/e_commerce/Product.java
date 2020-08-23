package com.example.e_commerce;

import android.database.Cursor;

import java.util.ArrayList;

public class Product {
    private final ArrayList<Item> items;
    private final ArrayList<Item> chosenItems;

    public Product(ProductDbHelper db) throws Exception {
        this.items = new ArrayList<>();
        this.chosenItems = new ArrayList();

        db.start();

        Cursor cursor = db.loadData();
        if(cursor == null || cursor.getCount() == 0) {
            throw new Exception("ERROR IN DB");
        }
        else {
            load(cursor);
        }
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
