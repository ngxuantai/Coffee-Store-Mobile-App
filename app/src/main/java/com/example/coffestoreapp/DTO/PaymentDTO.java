package com.example.coffestoreapp.DTO;

public class PaymentDTO {
    String DrinkName;
    int Quantity, Price;
    byte[] Image;

    public String getDrinkName() {
        return DrinkName;
    }

    public void setDrinkName(String drinkName) {
        this.DrinkName = drinkName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        this.Price = price;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        this.Image = image;
    }
}
