package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {
    static ArrayList<Item> itemList = new ArrayList<>();
    public static Double Total = 0.0;
    public static TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        itemList = Product.chosenItems;
        Total = countTotal();
        total = findViewById(R.id.total);
        total.setText("Total : " + Total);
        System.out.println(itemList.get(0).getName());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ShoppingCartAdapter adapter = new ShoppingCartAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(LayoutManager);
    }

    static Double countTotal() {
        Double sum = 0.0;
        for (int i = 0; i < itemList.size(); i++) {
            Double Cost = Double.parseDouble(itemList.get(i).getCost());
            int Quantity = itemList.get(i).getQuantity();
            sum += Cost*Quantity;
        }
        return sum;
    }
}

