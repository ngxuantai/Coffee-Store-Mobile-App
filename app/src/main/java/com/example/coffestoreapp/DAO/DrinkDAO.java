package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.DrinkDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DrinkDAO {
    SQLiteDatabase database;
    public DrinkDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addDrink(DrinkDTO drinkDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.DRINK_CATEGORY_ID, drinkDTO.getCategoryID());
        contentValues.put(CreateDatabase.DRINK_NAME, drinkDTO.getDrinkName());
        contentValues.put(CreateDatabase.DRINK_PRICE, drinkDTO.getPrice());
        contentValues.put(CreateDatabase.DRINK_IMAGE, drinkDTO.getImage());
        contentValues.put(CreateDatabase.DRINK_STATUS,"true");

        long check = database.insert(CreateDatabase.TABLE_DRINK,null,contentValues);

        if(check !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteDrink(int drinkId){
        long check = database.delete(CreateDatabase.TABLE_DRINK,CreateDatabase.DRINK_ID+ " = " +drinkId
                ,null);
        if(check !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean editDrink(DrinkDTO drinkDTO, int drinkId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.DRINK_CATEGORY_ID, drinkDTO.getCategoryID());
        contentValues.put(CreateDatabase.DRINK_NAME, drinkDTO.getDrinkName());
        contentValues.put(CreateDatabase.DRINK_PRICE, drinkDTO.getPrice());
        contentValues.put(CreateDatabase.DRINK_IMAGE, drinkDTO.getImage());
        contentValues.put(CreateDatabase.DRINK_STATUS, drinkDTO.getStatus());

        long check = database.update(CreateDatabase.TABLE_DRINK,contentValues,
                CreateDatabase.DRINK_ID+" = "+drinkId,null);
        if(check !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<DrinkDTO> getListDrinkByCategoryId(int categoryId){
        List<DrinkDTO> drinkDTOList = new ArrayList<DrinkDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TABLE_DRINK+ " WHERE " +CreateDatabase.DRINK_CATEGORY_ID+ " = '" +categoryId+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DrinkDTO drinkDTO = new DrinkDTO();
            drinkDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.DRINK_IMAGE)));
            drinkDTO.setDrinkName(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_NAME)));
            drinkDTO.setCategoryID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_CATEGORY_ID)));
            drinkDTO.setDrinkID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_ID)));
            drinkDTO.setPrice(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_PRICE)));
            drinkDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_STATUS)));
            drinkDTOList.add(drinkDTO);

            cursor.moveToNext();
        }
        return drinkDTOList;
    }

    public DrinkDTO getDrinkById(int drinkId){
        DrinkDTO drinkDTO = new DrinkDTO();
        String query = "SELECT * FROM "+CreateDatabase.TABLE_DRINK+" WHERE "+CreateDatabase.DRINK_ID+" = "+drinkId;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            drinkDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.DRINK_IMAGE)));
            drinkDTO.setDrinkName(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_NAME)));
            drinkDTO.setCategoryID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_CATEGORY_ID)));
            drinkDTO.setDrinkID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.DRINK_ID)));
            drinkDTO.setPrice(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_PRICE)));
            drinkDTO.setStatus(cursor.getString(cursor.getColumnIndex(CreateDatabase.DRINK_STATUS)));

            cursor.moveToNext();
        }
        return drinkDTO;
    }
}
