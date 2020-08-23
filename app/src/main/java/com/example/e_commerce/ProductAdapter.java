package com.example.e_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
    private final Product product;
    private final Context context;
    private LayoutInflater inflter;

    public ProductAdapter(Context context, Product product) {
        this.product = product;
        this.context = context;
        inflter = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return product.getItems().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.product_grid_adapter, null);
        TextView name = convertView.findViewById(R.id.product_name);
        name.setText(product.getItems().get(position).getName());
        TextView size = convertView.findViewById(R.id.product_size);
        size.setText(product.getItems().get(position).getSize());
        TextView price = convertView.findViewById(R.id.product_price);
        price.setText(product.getItems().get(position).getCost());
        return convertView;
    }
}
