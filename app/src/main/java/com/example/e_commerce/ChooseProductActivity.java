package com.example.e_commerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ChooseProductActivity extends AppCompatActivity {
    private Product p;
    private GridView gridView;
    private ShoppingCart shoppingCart;
    private TextView search;
    private Button cancel, removeResults;
    private ImageButton getVoiceInputBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);
        getSupportActionBar().hide();
        readProducts();
        gridView = findViewById(R.id.product_grid_view);
        Button shoppingCart = findViewById(R.id.ShoppingCartButton);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseProductActivity.this,ShoppingCartActivity.class));
            }
        });
        search = findViewById(R.id.txt_search_box);
        getVoiceInputBtn = findViewById(R.id.btn_voice_input);
        removeResults = findViewById(R.id.btn_remove_search_results);
        cancel = findViewById(R.id.btn_cancel);
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p);
        gridView.setAdapter(productAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p.addChosenItem(i);
                Toast.makeText(getApplicationContext(), p.getItems().get(i).getName(), Toast.LENGTH_LONG).show();
            }
        });

        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >=
                            (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        try {
                            gridView.setAdapter(new ProductAdapter(getApplicationContext(),
                                    new Product(new ProductDbHelper(getApplicationContext())
                                            , search.getText().toString())));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "ERROR!!!", Toast.LENGTH_LONG).show();
                        }

                        removeResults.setVisibility(View.VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });

        removeResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), p);
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
                // TODO: Finish cancel button implementation

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
                String text = result.get(0);
                search.setText(text);
            }
        }
    }
}