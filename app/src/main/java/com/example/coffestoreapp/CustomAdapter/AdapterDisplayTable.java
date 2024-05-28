package com.example.coffestoreapp.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//Todo: add home
import com.example.coffestoreapp.Activities.HomeActivity;
import com.example.coffestoreapp.Activities.PaymentActivity;
import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.DAO.OrderDAO;
import com.example.coffestoreapp.DTO.OrderDTO;
import com.example.coffestoreapp.DTO.TableDTO;
//Todo: add fragment displaycategory
//import com.example.coffestoreapp.Fragments.DisplayCategoryFragment;
import com.example.coffestoreapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener {
    Context context;
    int layout;
    List<TableDTO> tableDTOList;
    ViewHolder viewHolder;
    TableDAO tableDAO;
    OrderDAO orderDAO;
    FragmentManager fragmentManager;


    public AdapterDisplayTable(Context context, int layout, List<TableDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.tableDTOList = banAnDTOList;
        tableDAO = new TableDAO(context);
        orderDAO = new OrderDAO(context);
        //Todo: add home
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return tableDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return tableDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tableDTOList.get(position).getTableID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgTable = (ImageView) view.findViewById(R.id.img_customtable_table);
            viewHolder.imgCallOrder = (ImageView) view.findViewById(R.id.img_customtable_callOrder);
            viewHolder.imgPayment = (ImageView) view.findViewById(R.id.img_customtable_pay);
            viewHolder.imgPressButton = (ImageView) view.findViewById(R.id.img_customtable_pressButton);
            viewHolder.txtTableName = (TextView)view.findViewById(R.id.txt_customtable_tableName);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(tableDTOList.get(position).isSelected()){
            ShowButton();
        }else {
            PressButton();
        }

        TableDTO tableDTO = tableDTOList.get(position);

        String statusCheck = tableDAO.getStatusTableById(tableDTO.getTableID());
        //đổi hình theo tình trạng
        if(statusCheck.equals("true")){
            viewHolder.imgTable.setImageResource(R.drawable.ic_baseline_airline_seat_legroom_normal_40);
        }else {
            viewHolder.imgTable.setImageResource(R.drawable.ic_baseline_event_seat_40);
        }

        viewHolder.txtTableName.setText(tableDTO.getTableName());
        viewHolder.imgTable.setTag(position);

        //sự kiện click
        //Todo: Change to proper imgs
        viewHolder.imgTable.setOnClickListener(this);
        viewHolder.imgCallOrder.setOnClickListener(this);
        viewHolder.imgPayment.setOnClickListener(this);
        viewHolder.imgPressButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

        int pos1 = (int) viewHolder.imgTable.getTag();

        int tableID = tableDTOList.get(pos1).getTableID();
        String tableName = tableDTOList.get(pos1).getTableName();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderdate= dateFormat.format(calendar.getTime());

        switch (id){
            case R.id.img_customtable_table:
                int pos = (int)v.getTag();
                tableDTOList.get(pos).setSelected(true);
                ShowButton();
                break;

            case R.id.img_customtable_pressButton:
                PressButton();
                break;

            case R.id.img_customtable_callOrder:
                //Todo: add home activity
                Intent getIHome = ((HomeActivity)context).getIntent();
                //Todo: add home, change manv to employeeId
                int employeeId = getIHome.getIntExtra("employeeid",0);
                String status = tableDAO.getStatusTableById(tableID);

                if(status.equals("false")){
                    //Thêm bảng gọi món và update tình trạng bàn
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setTableID(tableID);
                    orderDTO.setEmployeeID(employeeId);
                    orderDTO.setDate(orderdate);
                    orderDTO.setStatus("false");
                    orderDTO.setTotalAmount("0");

                    long check = orderDAO.addOrder(orderDTO);
                    tableDAO.updateStatusTableById(tableID,"true");
                    if(check == 0){ Toast.makeText(context,context.getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show(); }
                }
                //chuyển qua trang catEditTableActivityegory
                //Todo: add displaytable fragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("tableid",tableID);
//                displayCategoryFragment.setArguments(bDataCategory);
                //Todo: add home , change hienthibanan to displaytable depending on the fragment
//                transaction.replace(R.id.contentView,displayCategoryFragment).addToBackStack("hienthibanan");
                transaction.commit();
                break;

            case R.id.img_customtable_pay:
                //chuyển dữ liệu qua trang thanh toán
                Intent iPayment = new Intent(context, PaymentActivity.class);
                iPayment.putExtra("tableid",tableID);
                iPayment.putExtra("tablename",tableName);
                iPayment.putExtra("orderdate",orderdate);
                context.startActivity(iPayment);
                break;
        }
    }

    private void ShowButton(){
        viewHolder.imgCallOrder.setVisibility(View.VISIBLE);
        viewHolder.imgPayment.setVisibility(View.VISIBLE);
        viewHolder.imgPressButton.setVisibility(View.VISIBLE);
    }
    private void PressButton(){
        viewHolder.imgCallOrder.setVisibility(View.INVISIBLE);
        viewHolder.imgPayment.setVisibility(View.INVISIBLE);
        viewHolder.imgPressButton.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgTable, imgCallOrder, imgPayment, imgPressButton;
        TextView txtTableName;
    }

}
