package com.example.e_commerce.ui.TabbedActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.e_commerce.products.MapsActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.login.Constants;
import com.example.e_commerce.login.UserDbHelper;
import com.example.e_commerce.ui.TabbedActivity.accountFragments.ProfileFragment;
import com.example.e_commerce.ui.TabbedActivity.accountFragments.ReviewFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity  extends AppCompatActivity {
    public static UserDbHelper databaseHelper;
    public SharedPreferences loginSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        databaseHelper = new UserDbHelper(AccountActivity.this);
        loginSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getViewPagerItems());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private List<ViewPagerItem> getViewPagerItems() {
        List<ViewPagerItem> viewPagerItems = new ArrayList<>();
        viewPagerItems.add(new ViewPagerItem(getString(R.string.tab_text_1), ProfileFragment.newInstance()));
        viewPagerItems.add(new ViewPagerItem(getString(R.string.tab_text_2), ReviewFragment.newInstance()));
        return viewPagerItems;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account_link:
                startActivity(new Intent(AccountActivity.this, AccountActivity.class));
                return true;
            case R.id.location_link:
                startActivity(new Intent(AccountActivity.this, MapsActivity.class));

                return true;
            case R.id.logout_link:
                SharedPreferences.Editor editor =loginSharedPreferences.edit();
                editor.putString(Constants.UserTable.USERNAME,"");
                editor.putString(Constants.UserTable.EMAIL, "");
                editor.putString(Constants.UserTable.PASSWORD, "");
                editor.putString(Constants.UserTable.BIRTHDATE, "");
                editor.putBoolean(Constants.REMEMBER_ME, false);
                editor.apply();
                finish();
                return true;
        }
        return false;
    }
}