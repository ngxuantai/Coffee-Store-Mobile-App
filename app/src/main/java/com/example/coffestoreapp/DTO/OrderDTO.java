package com.example.coffestoreapp.DTO;

public class OrderDTO {
    int OrderID, TableID, EmployeeID;
    String Status, Date, TotalAmount;

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int maDonDat) {
        this.OrderID = maDonDat;
    }

    public int getTableID() {
        return TableID;
    }

    public void setTableID(int tableId) {
        this.TableID = tableId;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeId) {
        this.EmployeeID = employeeId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.TotalAmount = totalAmount;
    }
}
