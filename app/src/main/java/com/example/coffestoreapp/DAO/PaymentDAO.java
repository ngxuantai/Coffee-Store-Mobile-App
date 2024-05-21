package com.example.coffestoreapp.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.PaymentDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    SQLiteDatabase database;
    public PaymentDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public List<PaymentDTO> getListDrinkByOrderId(int orderId){
        List<PaymentDTO> paymentDTOS = new ArrayList<PaymentDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TABLE_ORDER_DETAIL+" ctdd,"+CreateDatabase.TABLE_DRINK+" mon WHERE "
                +"ctdd."+CreateDatabase.ORDER_DETAIL_DRINK_ID+" = mon."+CreateDatabase.DRINK_ID+" AND "
                + CreateDatabase.ORDER_DETAIL_ORDER_ID+" = '"+orderId+"'";

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setQuantity(cursor.getInt(cursor.getColumnIndex(CreateDatabase.ORDER_DETAIL_QUANTITY)));
            paymentDTO.setPrice(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_PRICE)));
            paymentDTO.setDrinkName(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_NAME)));
            paymentDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.DRINK_IMAGE)));
            paymentDTOS.add(paymentDTO);

            cursor.moveToNext();
        }

        return paymentDTOS;
    }
}
