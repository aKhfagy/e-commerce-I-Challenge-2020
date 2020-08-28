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
    private LayoutInflater inflater;
    private int itemIndex;

    public ProductAdapter(Context context, Product product, int itemIndex) {
        this.product = product;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.itemIndex = itemIndex;
    }

    @Override
    public int getCount() {
        return product.getItems(itemIndex).size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.product_grid_adapter, null);
        TextView name = convertView.findViewById(R.id.product_name);
        name.setText(product.getItems(itemIndex).get(position).getName());
        TextView size = convertView.findViewById(R.id.product_size);
        size.setText(product.getItems(itemIndex).get(position).getSize());
        TextView price = convertView.findViewById(R.id.product_price);
        price.setText(product.getItems(itemIndex).get(position).getCost());
        return convertView;
    }
}
