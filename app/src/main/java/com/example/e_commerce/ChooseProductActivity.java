package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class ChooseProductActivity extends AppCompatActivity {
    private Product p;
    private GridView gridView;
    private ShoppingCart shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);
        readProducts();
        gridView = findViewById(R.id.product_grid_view);
        Button shoppingCart = findViewById(R.id.ShoppingCartButton);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseProductActivity.this,ShoppingCartActivity.class));
            }
        });
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