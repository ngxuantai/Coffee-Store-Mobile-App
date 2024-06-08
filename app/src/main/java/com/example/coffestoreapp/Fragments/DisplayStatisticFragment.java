package com.example.coffestoreapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.coffestoreapp.Activities.DetailStatisticActivity;
import com.example.coffestoreapp.Activities.HomeActivity;
import com.example.coffestoreapp.CustomAdapter.AdapterDisplayStatistic;
import com.example.coffestoreapp.DAO.OrderDAO;
import com.example.coffestoreapp.DTO.OrderDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class DisplayStatisticFragment extends Fragment {
    ListView lvStatistic;
    List<OrderDTO> orderDTOS;
    OrderDAO orderDAO;
    AdapterDisplayStatistic adapterDisplayStatistic;
    FragmentManager fragmentManager;
    int orderid, employeeid, tableid;
    String orderdate, totalamount;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaystatistic_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý thống kê");
        setHasOptionsMenu(true);

        lvStatistic = (ListView)view.findViewById(R.id.lvStatistic);
        orderDAO = new OrderDAO(getActivity());

        orderDTOS = orderDAO.getListOrder();
        adapterDisplayStatistic = new AdapterDisplayStatistic(getActivity(),R.layout.custom_layout_displaystatistic,orderDTOS);
        lvStatistic.setAdapter(adapterDisplayStatistic);
        adapterDisplayStatistic.notifyDataSetChanged();

        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderid = orderDTOS.get(position).getOrderID();
                employeeid = orderDTOS.get(position).getEmployeeID();
                tableid = orderDTOS.get(position).getTableID();
                orderdate = orderDTOS.get(position).getDate();
                totalamount = orderDTOS.get(position).getTotalAmount();

                Intent intent = new Intent(getActivity(), DetailStatisticActivity.class);
                intent.putExtra("orderId",orderid);
                intent.putExtra("employeeId",employeeid);
                intent.putExtra("tableId",tableid);
                intent.putExtra("orderDate",orderdate);
                intent.putExtra("totalAmount",totalamount);
                startActivity(intent);
            }
        });

        return view;
    }
}
