package com.example.e_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private final ArrayList<Item> product;
    private final Context context;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<Item> product) {
        this.product = product;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return product.size();
    }

    @Override
    public Object getItem(int position) {
        return product.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.product_grid_adapter, null);
        TextView name = convertView.findViewById(R.id.product_name);
        name.setText(product.get(position).getName());
        TextView price = convertView.findViewById(R.id.product_price);
        price.setText(product.get(position).getCost());
        return convertView;
    }
}
