package com.example.e_commerce;

import android.database.Cursor;

import java.util.ArrayList;

public class Product {
    private final ArrayList<Item>[] items;
    private final ArrayList<Item> chosenItems;

    public Product(ProductDbHelper db) throws Exception {
        this.chosenItems = new ArrayList();

        db.start();

        Cursor[] cursor = db.loadData();
        this.items = new ArrayList[cursor.length];

        for(int i = 0; i < cursor.length; ++i) {
            items[i] = new ArrayList<>();
        }

        if(cursor == null) {
            throw new Exception("ERROR IN DB");
        }
        else {
            load(cursor);
        }
    }

    public void load(Cursor[] cursors) {
        for(int i = 0; i < cursors.length; ++i) {
            while (cursors[i].moveToNext()) {
                items[i].add(new Item(cursors[i].getString(0), cursors[i].getString(1), cursors[i].getString(2)));
            }
            cursors[i].close();
        }
    }

    public void addChosenItem(int i, int position) {
        this.chosenItems.add(items[position].get(i));
    }

    public ArrayList<Item> getItems(int position) {
        return items[position];
    }

    public ArrayList<Item> getChosenItems() {
        return chosenItems;
    }
}
