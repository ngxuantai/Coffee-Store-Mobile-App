package com.example.coffestoreapp.DTO;

public class OrderDetailDTO {
    int OrderID, DrinkID, Quantity;

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderId) {
        this.OrderID = orderId;
    }

    public int getDrinkID() {
        return DrinkID;
    }

    public void setDrinkID(int drinkId) {
        this.DrinkID = drinkId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }
}
