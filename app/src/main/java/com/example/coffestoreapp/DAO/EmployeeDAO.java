package com.example.coffestoreapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    SQLiteDatabase database;
    public EmployeeDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long addEmployee(EmployeeDTO employeeDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.EMPLOYEE_FULLNAME, employeeDTO.getFullName());
        contentValues.put(CreateDatabase.EMPLOYEE_USERNAME, employeeDTO.getUserName());
        contentValues.put(CreateDatabase.EMPLOYEE_PASSWORD, employeeDTO.getPassword());
        contentValues.put(CreateDatabase.EMPLOYEE_EMAIL, employeeDTO.getEmail());
        contentValues.put(CreateDatabase.EMPLOYEE_PHONE, employeeDTO.getPhoneNumber());
        contentValues.put(CreateDatabase.EMPLOYEE_GENDER, employeeDTO.getGender());
        contentValues.put(CreateDatabase.EMPLOYEE_BIRTHDAY, employeeDTO.getBirthday());
        contentValues.put(CreateDatabase.EMPLOYEE_ROLE_ID, employeeDTO.getRoleId());

        long check = database.insert(CreateDatabase.TABLE_EMPLOYEE,null,contentValues);
        return check;
    }

    public long editEmployee(EmployeeDTO employeeDTO, int employeeId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.EMPLOYEE_FULLNAME, employeeDTO.getFullName());
        contentValues.put(CreateDatabase.EMPLOYEE_USERNAME, employeeDTO.getUserName());
        contentValues.put(CreateDatabase.EMPLOYEE_PASSWORD, employeeDTO.getPassword());
        contentValues.put(CreateDatabase.EMPLOYEE_EMAIL, employeeDTO.getEmail());
        contentValues.put(CreateDatabase.EMPLOYEE_PHONE, employeeDTO.getPhoneNumber());
        contentValues.put(CreateDatabase.EMPLOYEE_GENDER, employeeDTO.getGender());
        contentValues.put(CreateDatabase.EMPLOYEE_BIRTHDAY, employeeDTO.getBirthday());
        contentValues.put(CreateDatabase.EMPLOYEE_ROLE_ID, employeeDTO.getRoleId());

        long check = database.update(CreateDatabase.TABLE_EMPLOYEE,contentValues,
                CreateDatabase.EMPLOYEE_ID+" = "+employeeId,null);
        return check;
    }

    public int checkAuth(String username, String password){
        String query = "SELECT * FROM " +CreateDatabase.TABLE_EMPLOYEE+ " WHERE "
                +CreateDatabase.EMPLOYEE_USERNAME +" = '"+ username+"' AND "+CreateDatabase.EMPLOYEE_PASSWORD +" = '" +password +"'";
        int employeeId = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            employeeId = cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ID)) ;
            cursor.moveToNext();
        }
        return employeeId;
    }

    public boolean checkExistEmployee(){
        String query = "SELECT * FROM "+CreateDatabase.TABLE_EMPLOYEE;
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<EmployeeDTO> getListEmployee(){
        List<EmployeeDTO> employeeDTOS = new ArrayList<EmployeeDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TABLE_EMPLOYEE;

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setFullName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_FULLNAME)));
            employeeDTO.setEmail(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_EMAIL)));
            employeeDTO.setGender(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_GENDER)));
            employeeDTO.setBirthday(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_BIRTHDAY)));
            employeeDTO.setPhoneNumber(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PHONE)));
            employeeDTO.setUserName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_USERNAME)));
            employeeDTO.setPassword(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PASSWORD)));
            employeeDTO.setEmployId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ID)));
            employeeDTO.setRoleId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ROLE_ID)));

            employeeDTOS.add(employeeDTO);
            cursor.moveToNext();
        }
        return employeeDTOS;
    }

    public boolean deleteEmployee(int employeeId){
        long check = database.delete(CreateDatabase.TABLE_EMPLOYEE,CreateDatabase.EMPLOYEE_ID+ " = " +employeeId
                ,null);
        if(check !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public EmployeeDTO getEmployeeById(int employeeId){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        String query = "SELECT * FROM "+CreateDatabase.TABLE_EMPLOYEE+" WHERE "+CreateDatabase.EMPLOYEE_ID+" = "+employeeId;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            employeeDTO.setFullName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_FULLNAME)));
            employeeDTO.setEmail(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_EMAIL)));
            employeeDTO.setGender(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_GENDER)));
            employeeDTO.setBirthday(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_BIRTHDAY)));
            employeeDTO.setPhoneNumber(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PHONE)));
            employeeDTO.setUserName(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_USERNAME)));
            employeeDTO.setPassword(cursor.getString(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_PASSWORD)));
            employeeDTO.setEmployId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ID)));
            employeeDTO.setRoleId(cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ROLE_ID)));

            cursor.moveToNext();
        }
        return employeeDTO;
    }

    public int getRoleEmployee(int employeeId){
        int roleId = 0;
        String query = "SELECT * FROM "+CreateDatabase.TABLE_EMPLOYEE+" WHERE "+CreateDatabase.EMPLOYEE_ID+" = "+employeeId;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            roleId = cursor.getInt(cursor.getColumnIndex(CreateDatabase.EMPLOYEE_ROLE_ID));

            cursor.moveToNext();
        }
        return roleId;
    }


}
