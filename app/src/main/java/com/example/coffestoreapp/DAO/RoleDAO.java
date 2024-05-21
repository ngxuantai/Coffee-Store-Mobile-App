package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.Database.CreateDatabase;

public class RoleDAO {
    SQLiteDatabase database;
    public RoleDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public void addRole(String roleName){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.ROLE_NAME,roleName);
        database.insert(CreateDatabase.TABLE_ROLE,null,contentValues);
    }


    public String getRoleById(int roleId){
        String roleName ="";
        String query = "SELECT * FROM "+CreateDatabase.TABLE_ROLE+" WHERE "+CreateDatabase.ROLE_ID+" = "+roleId;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            roleName = cursor.getString(cursor.getColumnIndex(CreateDatabase.ROLE_NAME));
            cursor.moveToNext();
        }
        return roleName;
    }
}
