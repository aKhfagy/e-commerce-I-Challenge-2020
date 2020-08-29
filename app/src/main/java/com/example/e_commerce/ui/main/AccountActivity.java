package com.example.e_commerce.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.e_commerce.R;
import com.example.e_commerce.login.LoginDbHelper;
import com.example.e_commerce.ui.main.accountFragments.ProfileFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity  extends AppCompatActivity {
    public static LoginDbHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        databaseHelper = new LoginDbHelper(AccountActivity.this);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getViewPagerItems());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private List<ViewPagerItem> getViewPagerItems() {
        List<ViewPagerItem> viewPagerItems = new ArrayList<>();
        viewPagerItems.add(new ViewPagerItem(getString(R.string.tab_text_1), ProfileFragment.newInstance()));
        //viewPagerItems.add(new ViewPagerItem(getString(R.string.tab_text_2), CameraFragment.newInstance()));

        return viewPagerItems;
    }
}