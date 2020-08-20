package com.example.e_commerce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!loggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        setContentView(R.layout.activity_main);
        ProductDbHelper db = new ProductDbHelper(this);
        Product p = new Product();
        Cursor cursor = db.loadData();
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        } else {
            p.load(cursor);
        }
    }
}