package com.example.coffestoreapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffestoreapp.CustomAdapter.AdapterDisplayPayment;
import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.DAO.EmployeeDAO;
import com.example.coffestoreapp.DAO.PaymentDAO;
import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.DTO.PaymentDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {
    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_orderId,txt_detailstatistic_orderDate,txt_detailstatistic_tableName
            ,txt_detailstatistic_employeeName,txt_detailstatistic_totalAmount;
    GridView gvDetailStatistic;
    int orderId, employeeId, tableId;
    String orderDate, totalAmount;
    EmployeeDAO employeeDAO;
    TableDAO tableDAO;
    List<PaymentDTO> paymentDTOList;
    PaymentDAO paymentDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailstatistic_layout);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        orderId = intent.getIntExtra("orderId",0);
        employeeId = intent.getIntExtra("employeeId",0);
        tableId = intent.getIntExtra("tableId",0);
        orderDate = intent.getStringExtra("orderDate");
        totalAmount = intent.getStringExtra("totalAmount");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_orderId = (TextView)findViewById(R.id.txt_detailstatistic_orderId);
        txt_detailstatistic_orderDate = (TextView)findViewById(R.id.txt_detailstatistic_orderDate);
        txt_detailstatistic_tableName = (TextView)findViewById(R.id.txt_detailstatistic_tableName);
        txt_detailstatistic_employeeName = (TextView)findViewById(R.id.txt_detailstatistic_employeeName);
        txt_detailstatistic_totalAmount = (TextView)findViewById(R.id.txt_detailstatistic_totalAmount);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        //khởi tạo lớp dao mở kết nối csdl
        employeeDAO = new EmployeeDAO(this);
        tableDAO = new TableDAO(this);
        paymentDAO = new PaymentDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (orderId !=0){
            txt_detailstatistic_orderId.setText("Mã đơn: "+orderId);
            txt_detailstatistic_orderDate.setText(orderDate);
            txt_detailstatistic_totalAmount.setText(totalAmount+" VNĐ");

            EmployeeDTO employeeDTO = employeeDAO.getEmployeeById(employeeId);
            txt_detailstatistic_employeeName.setText(employeeDTO.getFullName());
            txt_detailstatistic_tableName.setText(tableDAO.getTableNameById(tableId));

            DisplayDetailStatisticList();
        }

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void DisplayDetailStatisticList(){
        paymentDTOList = paymentDAO.getListDrinkByOrderId(orderId);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,paymentDTOList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}
