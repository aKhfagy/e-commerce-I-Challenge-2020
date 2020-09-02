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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.login.Constants;
import com.example.e_commerce.ui.TabbedActivity.AccountActivity;

import java.util.ArrayList;
import java.util.Locale;

public class ChooseProductActivity extends AppCompatActivity implements RecyclerViewEvent {
    private Product p;
    private EditText search;
    private Button removeResults;
    private int index = 0, dummy = 0;
    public SharedPreferences loginSharedPreferences;
    private ArrayList<CategoryButtonItem> categoryButtonItems;
    private RecyclerView recyclerView, rvProduct;
    private CategoryButtonAdapter categoryButtonAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);
        setCategoryButtonItems();
        readProducts();
        rvProduct = findViewById(R.id.rv_product);
        final Button shoppingCart = findViewById(R.id.btn_shoping_cart);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingCartIntent = new Intent(ChooseProductActivity.this,
                        ShoppingCartActivity.class);
                startActivity(shoppingCartIntent);
            }
        });
        search = findViewById(R.id.txt_search_box);
        recyclerView = findViewById(R.id.rv_category_buttons);
        ImageButton getVoiceInputBtn = findViewById(R.id.btn_voice_input);
        removeResults = findViewById(R.id.btn_remove_search_results);
        Button cancel = findViewById(R.id.btn_cancel);
        setAdapterList(index);
        loginSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        categoryButtonAdapter = new CategoryButtonAdapter(getApplicationContext(),
                categoryButtonItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryButtonAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this, recyclerView,
                new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(p.getItems().length == 1)
                    readProducts();
                setAdapterList(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        rvProduct.addOnItemTouchListener(new RecyclerViewClickListener(this, rvProduct,
                new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                p.getChosenItems().add(p.getItems(index).get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        search.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >=
                            (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT]
                                    .getBounds().width())) {
                        String s = search.getText().toString();
                        ArrayList<Item> searched = p.getSearchedItems(index, s);

                        if(searched.size() == 0) {
                            Toast.makeText(getApplicationContext(), "No Items Have That name",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            p = new Product(searched);
                            dummy = index;
                            setAdapterList(0);
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
                index = dummy;
                setAdapterList(index);
                removeResults.setVisibility(View.INVISIBLE);
                search.setText("");
            }
        });

        getVoiceInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
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
                    Toast.makeText(getApplicationContext(), "Removed all items from cart",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cart is already empty!",
                            Toast.LENGTH_SHORT).show();
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
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                try {
                    assert result != null;
                    String text = result.get(0);
                    search.setText(text);
                }catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Unexpected error, please try again.",
                            Toast.LENGTH_SHORT).show();
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
                editor.putString(Constants.UserTable.ID,"");
                editor.putBoolean(Constants.REMEMBER_ME, false);
                editor.apply();
                ShoppingCartActivity.itemList.clear();
                finish();
                return true;
        }
        return false;
    }

    public void setAdapterList(int pos) {
        index = pos;
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(),
                p.getItems(index), this);
        rvProduct.setLayoutManager(new GridLayoutManager(this, 3));
        rvProduct.setAdapter(productAdapter);
    }

    private void setCategoryButtonItems() {
        categoryButtonItems = new ArrayList<>();
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.food));
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.nuggets));
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.salad));
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.happy_meal));
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.drinks));
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.cone));
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.breakfast));
        categoryButtonItems.add(new CategoryButtonItem(R.drawable.platter));
    }

    @Override
    public void onCategoryButtonClick(int position) {

    }

    @Override
    public void update(int position) {

    }

    @Override
    public void delete(int position) {

    }
}