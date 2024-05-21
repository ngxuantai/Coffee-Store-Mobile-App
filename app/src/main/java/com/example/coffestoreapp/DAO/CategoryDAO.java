package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.CategoryDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    SQLiteDatabase database;
    public CategoryDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addCategory(CategoryDTO categoryDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.CATEGORY_NAME, categoryDTO.getCategoryName());
        contentValues.put(CreateDatabase.CATEGORY_IMAGE, categoryDTO.getImage());
        long check = database.insert(CreateDatabase.TABLE_CATEGORY,null,contentValues);

        if(check != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteCategory(int categoryId){
        long check = database.delete(CreateDatabase.TABLE_CATEGORY,CreateDatabase.CATEGORY_ID+ " = " +categoryId
                ,null);
        if(check !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean editCategory(CategoryDTO categoryDTO, int categoryId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.CATEGORY_NAME, categoryDTO.getCategoryName());
        contentValues.put(CreateDatabase.CATEGORY_IMAGE, categoryDTO.getImage());
        long check = database.update(CreateDatabase.TABLE_CATEGORY,contentValues
                ,CreateDatabase.CATEGORY_ID+" = "+categoryId,null);
        if(check != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<CategoryDTO> getListCategory(){
        List<CategoryDTO> categoryDTOList = new ArrayList<CategoryDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TABLE_CATEGORY;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.CATEGORY_ID)));
            categoryDTO.setCategoryName(cursor.getString(cursor.getColumnIndex(CreateDatabase.CATEGORY_NAME)));
            categoryDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.CATEGORY_IMAGE)));
            categoryDTOList.add(categoryDTO);

            cursor.moveToNext();
        }
        return categoryDTOList;
    }

    public CategoryDTO getCategoryById(int categoryId){
        CategoryDTO categoryDTO = new CategoryDTO();
        String query = "SELECT * FROM " +CreateDatabase.TABLE_CATEGORY+" WHERE "+CreateDatabase.CATEGORY_ID+" = "+categoryId;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            categoryDTO.setCategoryID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.CATEGORY_ID)));
            categoryDTO.setCategoryName(cursor.getString(cursor.getColumnIndex(CreateDatabase.CATEGORY_NAME)));
            categoryDTO.setImage(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.CATEGORY_IMAGE)));

            cursor.moveToNext();
        }
        return categoryDTO;
    }

}
