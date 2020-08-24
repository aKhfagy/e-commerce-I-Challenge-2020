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
        if (!MainActivity.loggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(MainActivity.this, ChooseProductActivity.class));
            finish();
        }
    }
}