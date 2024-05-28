package com.example.coffestoreapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.DAO.EmployeeDAO;
import com.example.coffestoreapp.DTO.TableDTO;
import com.example.coffestoreapp.DTO.OrderDTO;
import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {
    Context context;
    int layout;
    List<OrderDTO> orderDTOS;
    ViewHolder viewHolder;
    EmployeeDAO employeeDAO;
    TableDAO tableDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<OrderDTO> orderDTOS){
        this.context = context;
        this.layout = layout;
        this.orderDTOS = orderDTOS;
        employeeDAO = new EmployeeDAO(context);
        tableDAO = new TableDAO(context);
    }

    @Override
    public int getCount() {
        return orderDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return orderDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderDTOS.get(position).getOrderID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_orderId = (TextView)view.findViewById(R.id.txt_customstatistic_orderId);
            viewHolder.txt_customstatistic_orderDate = (TextView)view.findViewById(R.id.txt_customstatistic_orderDate);
            viewHolder.txt_customstatistic_staffName = (TextView)view.findViewById(R.id.txt_customstatistic_staffName);
            viewHolder.txt_customstatistic_totalAmount = (TextView)view.findViewById(R.id.txt_customstatistic_totalAmount);
            viewHolder.txt_customstatistic_status = (TextView)view.findViewById(R.id.txt_customstatistic_status);
            viewHolder.txt_customstatistic_orderTable = (TextView)view.findViewById(R.id.txt_customstatistic_orderTable);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        OrderDTO orderDTO = orderDTOS.get(position);

        viewHolder.txt_customstatistic_orderId.setText("Mã đơn: "+orderDTO.getOrderID());
        viewHolder.txt_customstatistic_orderDate.setText(orderDTO.getDate());
        viewHolder.txt_customstatistic_totalAmount.setText(orderDTO.getTotalAmount()+" VNĐ");
        if (orderDTO.getStatus().equals("true"))
        {
            viewHolder.txt_customstatistic_status.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_status.setText("Chưa thanh toán");
        }
        EmployeeDTO employeeDTO = employeeDAO.getEmployeeById(orderDTO.getEmployeeID());
        viewHolder.txt_customstatistic_staffName.setText(employeeDTO.getFullName());
        viewHolder.txt_customstatistic_orderTable.setText(tableDAO.getTableNameById(orderDTO.getTableID()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_orderId, txt_customstatistic_orderDate, txt_customstatistic_staffName
                ,txt_customstatistic_totalAmount,txt_customstatistic_status, txt_customstatistic_orderTable;

    }
}
