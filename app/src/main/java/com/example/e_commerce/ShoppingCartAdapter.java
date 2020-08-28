package com.example.e_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    Context context;
    ArrayList<Item> itemList;

    public ShoppingCartAdapter(Context context, ArrayList<Item> List){
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Name.setText(itemList.get(position).getName());
        holder.Cost.setText(itemList.get(position).getCost());
        holder.Size.setText(itemList.get(position).getSize());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Cost,Size;
        public ViewHolder(View view){
            super(view);
            Name = view.findViewById(R.id.item_name);
            Cost = view.findViewById(R.id.item_cost);
            Size = view.findViewById(R.id.item_size);
        }
    }

}
