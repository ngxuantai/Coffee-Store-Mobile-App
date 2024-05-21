package com.example.coffestoreapp.DTO;

public class DrinkDTO {
    int DrinkID, CategoryID;
    String DrinkName, Price, Status;
    byte[] Image;

    public int getDrinkID() {
        return DrinkID;
    }

    public void setDrinkID(int drinkId) {
        this.DrinkID = drinkId;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryId) {
        this.CategoryID = categoryId;
    }

    public String getDrinkName() {
        return DrinkName;
    }

    public void setDrinkName(String drinkName) {
        this.DrinkName = drinkName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        this.Status = status;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        this.Image = image;
    }

}
