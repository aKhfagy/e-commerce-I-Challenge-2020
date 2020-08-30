package com.example.e_commerce;

public interface CategoryButtonEvent {
    void onCategoryButtonClick(int position);
    void update(int position);
    void delete(int position);
}
