package com.example.e_commerce.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.products.Item;
import com.example.e_commerce.R;

import java.util.ArrayList;

import static com.example.e_commerce.shoppingcart.ShoppingCartActivity.Total;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    Context context;
    ArrayList<Item> itemList;

    public ShoppingCartAdapter(Context context, ArrayList<Item> List) {
        this.context = context;
        this.itemList = List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.Name.setText(itemList.get(position).getName());
        holder.Cost.setText(itemList.get(position).getCost() + "$");
        holder.Quantity.setText(itemList.get(position).getQuantity() + "");

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.get(position).incrementQuantity();
                holder.Quantity.setText(itemList.get(position).getQuantity() + "");
                double Cost = Double.parseDouble(itemList.get(position).getCost());
                int Quantity = itemList.get(position).getQuantity();
                holder.Cost.setText(Cost * Quantity + "$");
                Total = ShoppingCartActivity.countTotal();
                ShoppingCartActivity.total.setText("Total : " + Total+ "\n proceed to cheeckout");
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.get(position).decrementQuantity();
                holder.Quantity.setText(itemList.get(position).getQuantity() + "");
                double Cost = Double.parseDouble(itemList.get(position).getCost());
                int Quantity = itemList.get(position).getQuantity();
                holder.Cost.setText(Cost * Quantity + "$");
                Total = ShoppingCartActivity.countTotal();
                ShoppingCartActivity.total.setText("Total : " + Total+ "\n proceed to cheeckout");
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.remove(itemList.get(holder.getAdapterPosition()));
                notifyItemRemoved(holder.getAdapterPosition());
                Total = ShoppingCartActivity.countTotal();
                ShoppingCartActivity.total.setText("Total : " + Total + "\n proceed to cheeckout");
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Cost, Quantity;
        Button plus, minus, remove;

        public ViewHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.item_name);
            Cost = view.findViewById(R.id.item_cost);
            Quantity = view.findViewById(R.id.item_quantity);
            plus = view.findViewById(R.id.plus_Button);
            minus = view.findViewById(R.id.minus_button);
            remove = view.findViewById(R.id.remove_item);
        }
    }



}
