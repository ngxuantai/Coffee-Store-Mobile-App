package com.example.coffestoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffestoreapp.DAO.OrderDAO;
import com.example.coffestoreapp.DAO.OrderDetailDAO;
import com.example.coffestoreapp.DTO.OrderDetailDTO;
import com.example.coffestoreapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class AmountMenuActivity extends AppCompatActivity {

    TextInputLayout TXTL_amountmenu_quantity;
    Button BTN_amountmenu_save;
    int tableId, drinkId;
    OrderDAO orderDAO;
    OrderDetailDAO orderDetailDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_menu_layout);

        // Lấy đối tượng view
        TXTL_amountmenu_quantity = (TextInputLayout) findViewById(R.id.txtl_amountmenu_quantity);
        BTN_amountmenu_save = (Button) findViewById(R.id.btn_amountmenu_save);

        // khởi tạo kết nối csdl
        orderDAO = new OrderDAO(this);
        orderDetailDAO = new OrderDetailDAO(this);

        // Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableId", 0);
        drinkId = intent.getIntExtra("drinkId", 0);

        BTN_amountmenu_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateAmount()) {
                    return;
                }

                int orderId = (int) orderDAO.getOrderIdByTableId(tableId, "false");
                boolean ktra = orderDetailDAO.checkDrinkExist(orderId, drinkId);
                if (ktra) {
                    // update số lượng món đã chọn
                    int oldQuantity = orderDetailDAO.getNumberDrinkByOrderId(orderId, drinkId);
                    int newQuantity = Integer.parseInt(TXTL_amountmenu_quantity.getEditText().getText().toString());
                    int total = oldQuantity + newQuantity;

                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setDrinkID(drinkId);
                    orderDetailDTO.setOrderID(orderId);
                    orderDetailDTO.setQuantity(total);

                    boolean checkUpdate = orderDetailDAO.updateNumber(orderDetailDTO);
                    if (checkUpdate) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // thêm số lượng món nếu chưa chọn món này
                    int sluong = Integer.parseInt(TXTL_amountmenu_quantity.getEditText().getText().toString());
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setDrinkID(drinkId);
                    orderDetailDTO.setOrderID(orderId);
                    orderDetailDTO.setQuantity(sluong);

                    boolean checkAdd = orderDetailDAO.addOrderDetail(orderDetailDTO);
                    if (checkAdd) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_sucessful),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // validate số lượng
    private boolean validateAmount() {
        String val = TXTL_amountmenu_quantity.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            TXTL_amountmenu_quantity.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (!val.matches(("\\d+(?:\\.\\d+)?"))) {
            TXTL_amountmenu_quantity.setError("Số lượng không hợp lệ");
            return false;
        } else {
            TXTL_amountmenu_quantity.setError(null);
            TXTL_amountmenu_quantity.setErrorEnabled(false);
            return true;
        }
    }
}