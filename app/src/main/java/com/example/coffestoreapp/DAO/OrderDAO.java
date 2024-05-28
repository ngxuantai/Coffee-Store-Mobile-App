package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.OrderDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    SQLiteDatabase database;
    public OrderDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long addOrder(OrderDTO orderDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.ORDER_TABLE_ID, orderDTO.getTableID());
        contentValues.put(CreateDatabase.ORDER_EMPLOYEE_ID, orderDTO.getEmployeeID());
        contentValues.put(CreateDatabase.ORDER_DATE, orderDTO.getDate());
        contentValues.put(CreateDatabase.ORDER_STATUS, orderDTO.getStatus());
        contentValues.put(CreateDatabase.ORDER_TOTAL_AMOUNT, orderDTO.getTotalAmount());

        long orderId = database.insert(CreateDatabase.TABLE_ORDERS,null,contentValues);

        return orderId;
    }

    public List<OrderDTO> getListOrder(){
        List<OrderDTO> orderDTOS = new ArrayList<OrderDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TABLE_ORDERS;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_ID)));
            orderDTO.setTableID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_TABLE_ID)));
            orderDTO.setTotalAmount(cursor.getString(cursor.getColumnIndex(CreateDatabase.ORDER_TOTAL_AMOUNT)));
            orderDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.ORDER_STATUS)));
            orderDTO.setDate(cursor.getString(cursor.getColumnIndex(CreateDatabase.ORDER_DATE)));
            orderDTO.setEmployeeID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_EMPLOYEE_ID)));
            orderDTOS.add(orderDTO);

            cursor.moveToNext();
        }
        return orderDTOS;
    }

    public List<OrderDTO> getListOrderByDate(String date){
        List<OrderDTO> orderDTOS = new ArrayList<OrderDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TABLE_ORDERS+" WHERE "+CreateDatabase.ORDER_DATE+" like '"+date+"'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_ID)));
            orderDTO.setTableID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_TABLE_ID)));
            orderDTO.setTotalAmount(cursor.getString(cursor.getColumnIndex(CreateDatabase.ORDER_TOTAL_AMOUNT)));
            orderDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.ORDER_STATUS)));
            orderDTO.setDate(cursor.getString(cursor.getColumnIndex(CreateDatabase.ORDER_DATE)));
            orderDTO.setEmployeeID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_EMPLOYEE_ID)));
            orderDTOS.add(orderDTO);

            cursor.moveToNext();
        }
        return orderDTOS;
    }

    public long getOrderIdByTableId(int tableId, String status){
        String query = "SELECT * FROM " +CreateDatabase.TABLE_ORDERS+ " WHERE " +CreateDatabase.ORDER_TABLE_ID+ " = '" +tableId+ "' AND "
                +CreateDatabase.ORDER_STATUS+ " = '" +status+ "' ";
        long magoimon = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_ID));

            cursor.moveToNext();
        }
        return magoimon;
    }

    public boolean UpdateOrderTotal(int orderId, String totalAmount){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.ORDER_TOTAL_AMOUNT,totalAmount);
        long check  = database.update(CreateDatabase.TABLE_ORDERS,contentValues,
                CreateDatabase.ORDER_ID+" = "+orderId,null);
        if (check != 0) {
            return true;
        }else{
            return false;
        }
    }

    public boolean UpdateOrderStatusByTableId(int tableId, String status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.ORDER_STATUS,status);
        long check = database.update(CreateDatabase.TABLE_ORDERS,contentValues,CreateDatabase.ORDER_TABLE_ID+
                " = '"+tableId+"'",null);
        if(check !=0){
            return true;
        }else {
            return false;
        }
    }
}
