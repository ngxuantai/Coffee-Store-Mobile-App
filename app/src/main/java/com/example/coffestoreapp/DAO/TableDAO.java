package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.TableDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    SQLiteDatabase database;

    public TableDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean addTable(String tableName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TABLE_NAME, tableName);
        contentValues.put(CreateDatabase.TABLE_STATUS, "false");

        long check = database.insert(CreateDatabase.TABLE_TABLES, null, contentValues);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTableById(int tableId) {
        long check = database.delete(CreateDatabase.TABLE_TABLES, CreateDatabase.TABLE_ID + " = " + tableId, null);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTableName(int tableId, String tableName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TABLE_NAME, tableName);

        long check = database.update(CreateDatabase.TABLE_TABLES, contentValues,
                CreateDatabase.TABLE_ID + " = '" + tableId + "' ", null);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<TableDTO> GetAllTables() {
        List<TableDTO> tableDTOList = new ArrayList<TableDTO>();
        String query = "SELECT * FROM " + CreateDatabase.TABLE_TABLES;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TableDTO tableDTO = new TableDTO();
            tableDTO.setTableID(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TABLE_ID)));
            tableDTO.setTableName(cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_NAME)));

            tableDTOList.add(tableDTO);
            cursor.moveToNext();
        }
        return tableDTOList;
    }

    public String getStatusTableById(int tableId) {
        String tableStatus = "";
        String query = "SELECT * FROM " + CreateDatabase.TABLE_TABLES + " WHERE " + CreateDatabase.TABLE_ID + " = '"
                + tableId + "' ";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tableStatus = cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_STATUS));
            cursor.moveToNext();
        }

        return tableStatus;
    }

    public boolean updateStatusTableById(int tableId, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TABLE_STATUS, status);

        long check = database.update(CreateDatabase.TABLE_TABLES, contentValues,
                CreateDatabase.TABLE_ID + " = '" + tableId + "' ", null);
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getTableNameById(int tableId) {
        String tableName = "";
        String query = "SELECT * FROM " + CreateDatabase.TABLE_TABLES + " WHERE " + CreateDatabase.TABLE_ID + " = '"
                + tableId + "' ";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tableName = cursor.getString(cursor.getColumnIndex(CreateDatabase.TABLE_NAME));
            cursor.moveToNext();
        }

        return tableName;
    }
}
