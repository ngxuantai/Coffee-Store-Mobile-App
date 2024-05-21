package com.example.coffestoreapp.DTO;

public class CategoryDTO {
    int CategoryID;
    String CategoryName;
    byte[] Image;


    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryId) {
        this.CategoryID = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.CategoryName = categoryName;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        this.Image = image;
    }
}
