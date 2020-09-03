package com.example.e_commerce.products;

import android.database.Cursor;

import java.util.ArrayList;

public class Product {
    private final ArrayList<Item>[] items;
    public static final ArrayList<Item> chosenItems = new ArrayList<>();

    public Product(ArrayList<Item> items) {
        this.items = new ArrayList[1];
        this.items[0] = items;
    }

    public Product(ProductDbHelper db) throws Exception {
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

    public ArrayList<Item>[] getItems() {
        return items;
    }

    public ArrayList<Item> getChosenItems() {
        return chosenItems;
    }

    public ArrayList<Item> getSearchedItems(int position, String s) {
        ArrayList<Item> ret = new ArrayList<>();
        for(int i = 0; i < items[position].size(); ++i) {
            if(new FindSearchResult(s, items[position].get(i).getName()).find_pattern()) {
                ret.add(items[position].get(i));
            }
        }
        return ret;
    }

    private static class FindSearchResult {

        private String pattern, text;

        private FindSearchResult(String pattern, String text) {
            this.pattern = pattern.toLowerCase();
            this.text = text.toLowerCase();
        }

        private int[] computePrefixVector(String s) {
            int border = 0, sz = s.length();
            int[] ret = new int[sz];

            for(int i = 0; i < sz; ++i)
                ret[i] = 0;

            for (int i = 1; i < sz; ++i) {
                while (border > 0 && s.charAt(i) != s.charAt(border)) {
                    border = ret[border - 1];
                }

                if (s.charAt(i) == s.charAt(border)) {
                    ++border;
                    ret[i] = border;
                }

                if (border == 0) {
                    ret[i] = 0;
                }
            }

            return ret;
        }

        boolean find_pattern() {
            String s = pattern + '$' + text;
            int sz = pattern.length(), sz_s = s.length();

            int[] prefix = computePrefixVector(s);

            for (int i = sz + 1; i < sz_s; ++i) {
                if (prefix[i] == sz) {
                    return true;
                }
            }

            return false;
        }
    }

}
