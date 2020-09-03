package com.example.e_commerce.shoppingcart;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.e_commerce.R;
import com.example.e_commerce.login.Constants;
import com.example.e_commerce.login.UserDbHelper;
import com.example.e_commerce.products.Item;
import com.example.e_commerce.products.Product;
import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {
    public static ArrayList<Item> itemList = new ArrayList<>();
    public static double Total = 0;
    public static Button total;
    private SharedPreferences sharedPreferences;
    private UserDbHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        itemList = Product.chosenItems;
        sharedPreferences = ShoppingCartActivity.this.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        databaseHelper = new UserDbHelper(this);
        Total = countTotal();
        total = findViewById(R.id.total);
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
        total.setText("Total : " + Total+ "\n proceed to checkout");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        TextView EmptyCartTextView = findViewById(R.id.EmptyCartText);
        if (!itemList.isEmpty()){
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(this, itemList);
            recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(LayoutManager);
        }
        else {
            EmptyCartTextView.setVisibility(View.VISIBLE);
            total.setVisibility(View.INVISIBLE);
        }
    }

    static double countTotal() {
        double sum = 0.0;
        for (int i = 0; i < itemList.size(); i++) {
            Double Cost = Double.parseDouble(itemList.get(i).getCost());
            int Quantity = itemList.get(i).getQuantity();
            sum += Cost*Quantity;
        }
        return sum;
    }

    void sendMail(){
        String mail = databaseHelper.getUserEmail(sharedPreferences.getInt(Constants.UserTable.ID,-1));
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

