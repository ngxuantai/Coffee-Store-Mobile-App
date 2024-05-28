package com.example.coffestoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.coffestoreapp.CustomAdapter.AdapterDisplayPayment;
import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.DAO.OrderDAO;
import com.example.coffestoreapp.DAO.PaymentDAO;
import com.example.coffestoreapp.DTO.PaymentDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView IMG_payment_backbtn;
    TextView TXT_payment_tableName, TXT_payment_orderDate, TXT_payment_total;
    Button BTN_payment_Pay;
    GridView gvDisplayPayment;
    OrderDAO orderDAO;
    TableDAO tableDAO;
    PaymentDAO paymentDAO;
    List<PaymentDTO> paymentDTOS;
    AdapterDisplayPayment adapterDisplayPayment;
    long total = 0;
    int tableId, orderId;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        //region view properties
        gvDisplayPayment= (GridView)findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = (ImageView)findViewById(R.id.img_payment_backbtn);
        TXT_payment_tableName = (TextView)findViewById(R.id.txt_payment_TableName);
        TXT_payment_orderDate = (TextView)findViewById(R.id.txt_payment_OrderDate);
        TXT_payment_total = (TextView)findViewById(R.id.txt_payment_Pay);
        BTN_payment_Pay = (Button)findViewById(R.id.btn_payment_Pay);
        //endregion

        //khởi tạo kết nối csdl
        orderDAO = new OrderDAO(this);
        paymentDAO = new PaymentDAO(this);
        tableDAO = new TableDAO(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã bàn đc chọn
        Intent intent = getIntent();
        tableId = intent.getIntExtra("tableid",0);
        String tablename = intent.getStringExtra("tablename");
        String orderdate = intent.getStringExtra("orderdate");

        TXT_payment_tableName.setText(tablename);
        TXT_payment_orderDate.setText(orderdate);

        //ktra mã bàn tồn tại thì hiển thị
        if(tableId !=0 ){
            ShowPayment();

            for (int i = 0; i< paymentDTOS.size(); i++){
                int quantity = paymentDTOS.get(i).getQuantity();
                int price = paymentDTOS.get(i).getPrice();

                total += (quantity * price);
            }
            TXT_payment_total.setText(String.valueOf(total) +" VNĐ");
        }

        BTN_payment_Pay.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_Pay:
                boolean tableCheck = tableDAO.updateStatusTableById(tableId,"false");
                boolean orderCheck = orderDAO.UpdateOrderStatusByTableId(tableId,"true");
                boolean totalCheck = orderDAO.UpdateOrderTotal(orderId,String.valueOf(total));
                if(tableCheck && orderCheck && totalCheck){
                    ShowPayment();
                    TXT_payment_total.setText("0 VNĐ");
                    Toast.makeText(getApplicationContext(),"Thanh toán thành công!",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(),"Lỗi thanh toán!",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void ShowPayment(){
        orderId = (int) orderDAO.getOrderIdByTableId(tableId,"false");
        paymentDTOS = paymentDAO.getListDrinkByOrderId(orderId);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu, paymentDTOS);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}