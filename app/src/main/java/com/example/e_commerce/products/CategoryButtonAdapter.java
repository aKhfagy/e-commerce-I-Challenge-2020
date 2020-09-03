package com.example.e_commerce.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;

import java.util.ArrayList;

public class CategoryButtonAdapter  extends RecyclerView.Adapter<CategoryButtonAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CategoryButtonItem> categoryButtonItems;
    private RecyclerViewEvent event;

    public CategoryButtonAdapter(Context context, ArrayList<CategoryButtonItem> categoryButtonItems, RecyclerViewEvent event) {
        this.context = context;
        this.categoryButtonItems = categoryButtonItems;
        this.event = event;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_button_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryButtonItem categoryButtonItem = categoryButtonItems.get(position);
        holder.button.setImageResource(categoryButtonItem.getResourceNumber());
    }

    @Override
    public int getItemCount() {
        return categoryButtonItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition() != -1) {
                        event.onCategoryButtonClick(getAdapterPosition());
                    }
                }
            });


        }
    }
}
