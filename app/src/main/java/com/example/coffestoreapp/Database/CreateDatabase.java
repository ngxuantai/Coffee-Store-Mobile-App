package com.example.coffestoreapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {
        public static String TABLE_EMPLOYEE = "Employee";
        public static String TABLE_DRINK = "Drink";
        public static String TABLE_CATEGORY = "Category";
        public static String TABLE_TABLES = "Tables";
        public static String TABLE_ORDERS = "Orders";
        public static String TABLE_ORDER_DETAIL = "OrderDetail";
        public static String TABLE_ROLE = "Role";

        // Employee table
        public static String EMPLOYEE_ID = "EmployeeID";
        public static String EMPLOYEE_FULLNAME = "FullName";
        public static String EMPLOYEE_USERNAME = "Username";
        public static String EMPLOYEE_PASSWORD = "Password";
        public static String EMPLOYEE_EMAIL = "Email";
        public static String EMPLOYEE_PHONE = "Phone";
        public static String EMPLOYEE_GENDER = "Gender";
        public static String EMPLOYEE_BIRTHDAY = "Birthday";
        public static String EMPLOYEE_ROLE_ID = "RoleID";

        // Role table
        public static String ROLE_ID = "RoleID";
        public static String ROLE_NAME = "RoleName";

        // Drink table
        public static String DRINK_ID = "DrinkID";
        public static String DRINK_NAME = "DrinkName";
        public static String DRINK_PRICE = "Price";
        public static String DRINK_STATUS = "Status";
        public static String DRINK_IMAGE = "Image";
        public static String DRINK_CATEGORY_ID = "CategoryID";

        // Category table
        public static String CATEGORY_ID = "CategoryID";
        public static String CATEGORY_NAME = "CategoryName";
        public static String CATEGORY_IMAGE = "Image";

        // Table table
        public static String TABLE_ID = "TableID";
        public static String TABLE_NAME = "TableName";
        public static String TABLE_STATUS = "Status";

        // Order table
        public static String ORDER_ID = "OrderID";
        public static String ORDER_TABLE_ID = "TableID";
        public static String ORDER_EMPLOYEE_ID = "EmployeeID";
        public static String ORDER_DATE = "Date";
        public static String ORDER_STATUS = "Status";
        public static String ORDER_TOTAL_AMOUNT = "TotalAmount";

        // OrderDetail table
        public static String ORDER_DETAIL_ORDER_ID = "OrderID";
        public static String ORDER_DETAIL_DRINK_ID = "DrinkID";
        public static String ORDER_DETAIL_QUANTITY = "Quantity";

        public CreateDatabase(Context context) {
                super(context, "OrderDrink", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String tblEmployee = "CREATE TABLE " + TABLE_EMPLOYEE + " ( " + EMPLOYEE_ID
                                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + EMPLOYEE_FULLNAME + " TEXT, " + EMPLOYEE_USERNAME + " TEXT, " + EMPLOYEE_PASSWORD
                                + " TEXT, "
                                + EMPLOYEE_EMAIL + " TEXT, " + EMPLOYEE_PHONE + " TEXT, " + EMPLOYEE_GENDER + " TEXT, "
                                + EMPLOYEE_BIRTHDAY + " TEXT , " + EMPLOYEE_ROLE_ID + " INTEGER)";

                String tblRole = "CREATE TABLE " + TABLE_ROLE + " ( " + ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + ROLE_NAME + " TEXT)";

                String tblTable = "CREATE TABLE " + TABLE_TABLES + " ( " + TABLE_ID
                                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + TABLE_NAME + " TEXT, " + TABLE_STATUS + " TEXT )";

                String tblDrink = "CREATE TABLE " + TABLE_DRINK + " ( " + DRINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + DRINK_NAME + " TEXT, " + DRINK_PRICE + " TEXT, " + DRINK_STATUS + " TEXT, "
                                + DRINK_IMAGE + " BLOB, " + DRINK_CATEGORY_ID + " INTEGER )";

                String tblCategory = "CREATE TABLE " + TABLE_CATEGORY + " ( " + CATEGORY_ID
                                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + CATEGORY_IMAGE + " BLOB, " + CATEGORY_NAME + " TEXT)";

                String tblOrder = "CREATE TABLE " + TABLE_ORDERS + " ( " + ORDER_ID
                                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                + ORDER_TABLE_ID + " INTEGER, " + ORDER_EMPLOYEE_ID + " INTEGER, " + ORDER_DATE
                                + " TEXT, "
                                + ORDER_TOTAL_AMOUNT + " TEXT," + ORDER_STATUS + " TEXT )";

                String tblOrderDetail = "CREATE TABLE " + TABLE_ORDER_DETAIL + " ( " + ORDER_DETAIL_ORDER_ID
                                + " INTEGER, "
                                + ORDER_DETAIL_DRINK_ID + " INTEGER, " + ORDER_DETAIL_QUANTITY + " INTEGER, "
                                + " PRIMARY KEY ( " + ORDER_DETAIL_ORDER_ID + "," + ORDER_DETAIL_DRINK_ID + "))";

                db.execSQL(tblEmployee);
                db.execSQL(tblRole);
                db.execSQL(tblTable);
                db.execSQL(tblDrink);
                db.execSQL(tblCategory);
                db.execSQL(tblOrder);
                db.execSQL(tblOrderDetail);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public SQLiteDatabase open() {
                return this.getWritableDatabase();
        }
}