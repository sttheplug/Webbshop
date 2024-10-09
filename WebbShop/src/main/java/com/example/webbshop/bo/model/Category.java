package com.example.webbshop.bo.model;

import com.example.webbshop.ui.DTO.CategoryDTO;

public class Category {
    private int categoryId;
    private String categoryName;
    public Category(){

    }
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static CategoryDTO toDTO(Category category){
        return new CategoryDTO(category.getCategoryId(), category.getCategoryName());
    }
}