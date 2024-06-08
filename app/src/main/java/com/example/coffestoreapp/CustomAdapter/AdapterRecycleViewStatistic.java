package com.example.coffestoreapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.DAO.EmployeeDAO;
import com.example.coffestoreapp.DTO.OrderDTO;
import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder> {
    Context context;
    int layout;
    List<OrderDTO> orderDTOList;
    EmployeeDAO empolyeeDAO;
    TableDAO tableDAO;

    public AdapterRecycleViewStatistic(Context context, int layout, List<OrderDTO> orderDTOList) {

        this.context = context;
        this.layout = layout;
        this.orderDTOList = orderDTOList;
        empolyeeDAO = new EmployeeDAO(context);
        tableDAO = new TableDAO(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDTO orderDTO = orderDTOList.get(position);
        holder.txt_customstatistic_orderId.setText("Mã đơn: " + orderDTO.getOrderID());
        holder.txt_customstatistic_orderDate.setText(orderDTO.getDate());
        if (orderDTO.getTotalAmount().equals("0")) {
            holder.txt_customstatistic_totalAmount.setVisibility(View.INVISIBLE);
        } else {
            holder.txt_customstatistic_totalAmount.setVisibility(View.VISIBLE);
        }

        if (orderDTO.getStatus().equals("true")) {
            holder.txt_customstatistic_status.setText("Đã thanh toán");
        } else {
            holder.txt_customstatistic_status.setText("Chưa thanh toán");
        }
        EmployeeDTO employeeDTO = empolyeeDAO.getEmployeeById(orderDTO.getEmployeeID());
        holder.txt_customstatistic_staffName.setText(employeeDTO.getFullName());
        holder.txt_customstatistic_orderTable.setText(tableDAO.getTableNameById(orderDTO.getTableID()));
    }

    @Override
    public int getItemCount() {
        return orderDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_customstatistic_orderId, txt_customstatistic_orderDate, txt_customstatistic_staffName,
                txt_customstatistic_orderTable, txt_customstatistic_totalAmount, txt_customstatistic_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_customstatistic_orderId = itemView.findViewById(R.id.txt_customstatistic_orderId);
            txt_customstatistic_orderDate = itemView.findViewById(R.id.txt_customstatistic_orderDate);
            txt_customstatistic_staffName = itemView.findViewById(R.id.txt_customstatistic_staffName);
            txt_customstatistic_orderTable = itemView.findViewById(R.id.txt_customstatistic_orderTable);
            txt_customstatistic_totalAmount = itemView.findViewById(R.id.txt_customstatistic_totalAmount);
            txt_customstatistic_status = itemView.findViewById(R.id.txt_customstatistic_status);
        }
    }
}
