package com.example.coffestoreapp.DTO;

public class TableDTO {
    int TableID;
    String TableName;
    boolean Selected;


    public int getTableID() {
        return TableID;
    }

    public void setTableID(int tableId) {
        this.TableID = tableId;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        this.TableName = tableName;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        this.Selected = selected;
    }
}
