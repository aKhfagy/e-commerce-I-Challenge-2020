package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

public class ChooseProductActivity extends AppCompatActivity {
    private Product p;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);
        readProducts();
        gridView = findViewById(R.id.product_grid_view);
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p);
        gridView.setAdapter(productAdapter);
    }

    public void readProducts() {
        ProductDbHelper db = new ProductDbHelper(getApplicationContext());
        try {
            p = new Product(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}