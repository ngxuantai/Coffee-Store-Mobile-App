package com.example.coffestoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_addtable_tableName;
    Button BTN_addtable_createTable;
    TableDAO tableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtable_layout);

        //region Lấy đối tượng trong view
        TXTL_addtable_tableName = (TextInputLayout)findViewById(R.id.txtl_addtable_tablename);
        BTN_addtable_createTable = (Button)findViewById(R.id.btn_addtable_CreateTable);

        tableDAO = new TableDAO(this);
        BTN_addtable_createTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTableName = TXTL_addtable_tableName.getEditText().getText().toString();
                if(sTableName != null || !sTableName.isEmpty()){
                    boolean check = tableDAO.addTable(sTableName);
                    //trả về result cho displaytable
                    Intent intent = new Intent();
                    //Todo: rename check later, if no error occurs
                    intent.putExtra("ketquathem",check);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    //validate dữ liệu
    private boolean validateName(){
        String val = TXTL_addtable_tableName.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addtable_tableName.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addtable_tableName.setError(null);
            TXTL_addtable_tableName.setErrorEnabled(false);
            return true;
        }
    }
}