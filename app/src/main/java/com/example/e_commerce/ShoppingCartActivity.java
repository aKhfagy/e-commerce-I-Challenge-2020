package com.example.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.login.Constants;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {
    static ArrayList<Item> itemList = new ArrayList<>();
    public static Double Total = 0.0;
    public static Button total;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        itemList = Product.chosenItems;
        sharedPreferences = ShoppingCartActivity.this.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Total = countTotal();
        total = findViewById(R.id.total);
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
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

    void sendMail(){
        String mail = sharedPreferences.getString(Constants.UserTable.EMAIL,"");
        String message = "You Ordered: \n\n";
        for (int i = 0;i<itemList.size();i++){
            message += itemList.get(i).getQuantity() + " " + itemList.get(i).getName() + " : " + itemList.get(i).getCost() + "$" + "\n";
        }
        message += "\nYou will pay " + countTotal() + "$";
        String subject = "Confirmation Mail";
        MailAPI mailAPI = new MailAPI(this,mail,subject,message);
        mailAPI.execute();
    }

}

