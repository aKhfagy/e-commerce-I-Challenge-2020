package com.example.e_commerce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.login.Constants;
import com.example.e_commerce.ui.main.AccountActivity;

import java.util.ArrayList;
import java.util.Locale;

public class ChooseProductActivity extends AppCompatActivity {
    private Product p;
    private GridView gridView;
    private TextView search;
    private Button removeResults;
    private int index = 0;
    public SharedPreferences loginSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);
        readProducts();
        gridView = findViewById(R.id.product_grid_view);
        final Button shoppingCart = findViewById(R.id.btn_shoping_cart);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingCartIntent = new Intent(ChooseProductActivity.this, ShoppingCartActivity.class);
                startActivity(shoppingCartIntent);
            }
        });
        ImageButton btnMorningPlatters = findViewById(R.id.btn_morning_platters);
        ImageButton btnBreakfastMeals = findViewById(R.id.btn_breakfast_meals);
        ImageButton btnSweetTreats = findViewById(R.id.btn_sweet_treats);
        ImageButton btnMcCafe = findViewById(R.id.btn_mc_caffee);
        ImageButton btnHappyMeals = findViewById(R.id.btn_happy_meal);
        ImageButton btnFood = findViewById(R.id.btn_food);
        ImageButton btnNuggets = findViewById(R.id.btn_nuggets);
        ImageButton btnSalads = findViewById(R.id.btn_salads);
        search = findViewById(R.id.txt_search_box);
        ImageButton getVoiceInputBtn = findViewById(R.id.btn_voice_input);
        removeResults = findViewById(R.id.btn_remove_search_results);
        Button cancel = findViewById(R.id.btn_cancel);
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
        gridView.setAdapter(productAdapter);
        loginSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 0;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        btnNuggets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 1;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        btnSalads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 2;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        btnHappyMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 3;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        btnMcCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 4;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        btnSweetTreats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 5;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        btnBreakfastMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 6;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        btnMorningPlatters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getItems().length == 1)
                    readProducts();
                index = 7;
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p.addChosenItem(i, index);
                Toast.makeText(getApplicationContext(), p.getItems(index).get(i).getName()
                        + " is successfully added to the cart."
                        + "\nItems in Cart " + p.getChosenItems().size(), Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >=
                            (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        String s = search.getText().toString();
                        ArrayList<Item> searched = p.getSearchedItems(index, s);

                        if(searched.size() == 0) {
                            Toast.makeText(getApplicationContext(), "No Items Have That name", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            p = new Product(searched);
                            index = 0;
                            ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                            gridView.setAdapter(productAdapter);
                            removeResults.setVisibility(View.VISIBLE);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        removeResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readProducts();
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p, index);
                gridView.setAdapter(productAdapter);
                removeResults.setVisibility(View.INVISIBLE);
            }
        });

        getVoiceInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                if(intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, 10);
                else
                    Toast.makeText(getApplicationContext(),
                            "Your device doesn\'t support taking speech input..",
                            Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getChosenItems().size() > 0) {
                    p.getChosenItems().clear();
                    Toast.makeText(getApplicationContext(), "Removed all items from cart", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cart is already empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void readProducts() {
        ProductDbHelper db = new ProductDbHelper(getApplicationContext());
        try {
            p = new Product(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                try {
                    assert result != null;
                    String text = result.get(0);
                    search.setText(text);
                }catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Unexpected error, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
                 startActivity(new Intent(ChooseProductActivity.this, AccountActivity.class));
                return true;
            case R.id.location_link:
                startActivity(new Intent(ChooseProductActivity.this, MapsActivity.class));

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