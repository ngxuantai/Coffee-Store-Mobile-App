package com.example.coffestoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class EditTableActivity extends AppCompatActivity {
    TextInputLayout TXTL_edittable_tablename;
    Button BTN_edittable_EditTable;
    TableDAO tableDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittable_layout);

        //thuộc tính view
        TXTL_edittable_tablename = (TextInputLayout)findViewById(R.id.txtl_edittable_tablename);
        BTN_edittable_EditTable = (Button)findViewById(R.id.btn_edittable_EditTable);

        //khởi tạo dao mở kết nối csdl
        tableDAO = new TableDAO(this);
        int tableId = getIntent().getIntExtra("tableId",0); //lấy maban từ bàn đc chọn

        BTN_edittable_EditTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = TXTL_edittable_tablename.getEditText().getText().toString();

                if(tableName != null || tableName.equals("")){
                    boolean check = tableDAO.updateTableName(tableId,tableName);
                    Intent intent = new Intent();
                    //Todo: rename ketquasua if needed
                    intent.putExtra("editResult",check);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
