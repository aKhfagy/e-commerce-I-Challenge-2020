package com.example.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.login.User;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences loginSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginSharedPreferences = getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);

        if (loginSharedPreferences.getBoolean(User.REMEMBER_ME, false)) {
            startActivity(new Intent(MainActivity.this, ChooseProductActivity.class));

        }
        else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }
    }

}