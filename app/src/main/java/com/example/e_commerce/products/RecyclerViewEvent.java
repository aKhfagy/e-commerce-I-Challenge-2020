package com.example.e_commerce.products;

public interface RecyclerViewEvent {
    void onCategoryButtonClick(int position);
    void update(int position);
    void delete(int position);
}
