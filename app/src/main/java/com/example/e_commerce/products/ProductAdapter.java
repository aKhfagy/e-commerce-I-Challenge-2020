package com.example.e_commerce.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;

import java.util.ArrayList;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Item> items;
    private RecyclerViewEvent event;

    public ProductAdapter(Context context, ArrayList<Item> items, RecyclerViewEvent event) {
        this.context = context;
        this.items = items;
        this.event = event;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_grid_adapter,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = items.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("$" + item.getCost());
        if(item.getPath() != null && item.getPath().length() != 0) {
            String path = "@drawable/" + item.getPath();
            int res = context.getResources().getIdentifier(path, "drawable", context.getPackageName());
            holder.image.setImageResource(res);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView price;
        public final ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            image = itemView.findViewById(R.id.product_image);

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
