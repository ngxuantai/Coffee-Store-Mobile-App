package com.example.coffestoreapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.coffestoreapp.Activities.AddCategoryActivity;
import com.example.coffestoreapp.Activities.HomeActivity;
//import com.example.coffestoreapp.CustomAdapter.AdapterRecycleViewCategory;
//import com.example.coffestoreapp.CustomAdapter.AdapterRecycleViewStatistic;
import com.example.coffestoreapp.DAO.OrderDAO;
import com.example.coffestoreapp.DAO.CategoryDAO;
import com.example.coffestoreapp.DTO.OrderDTO;
import com.example.coffestoreapp.DTO.CategoryDTO;
import com.example.coffestoreapp.R;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayHomeFragment extends Fragment implements View.OnClickListener {
    RecyclerView rcv_displayhome_Category, rcv_displayhome_OrderInDay;
    RelativeLayout layout_displayhome_Statistic, layout_displayhome_ViewTable, layout_displayhome_ViewMenu, layout_displayhome_ViewEmployee;
    TextView txt_displayhome_ViewAllCategory, txt_displayhome_ViewAllStatistic;
    CategoryDAO categoryDAO;
    OrderDAO orderDAO;
    List<CategoryDTO> categoryDTOList;
    List<OrderDTO> orderDTOList;
//    AdapterRecycleViewCategory adapterRecycleViewCategory;
//    AdapterRecycleViewStatistic adapterRecycleViewStatistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.displayhome_layout, container, false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Trang chủ");
        setHasOptionsMenu(true);

        // region lay doi tuong view
        rcv_displayhome_Category = (RecyclerView)view.findViewById(R.id.rcv_displayhome_Category);
        rcv_displayhome_OrderInDay = (RecyclerView)view.findViewById(R.id.rcv_displayhome_OrderInDay);
        layout_displayhome_Statistic = (RelativeLayout)view.findViewById(R.id.layout_displayhome_Statistic);
        layout_displayhome_ViewTable = (RelativeLayout)view.findViewById(R.id.layout_displayhome_ViewTable);
        layout_displayhome_ViewMenu = (RelativeLayout)view.findViewById(R.id.layout_displayhome_ViewMenu);
        layout_displayhome_ViewEmployee = (RelativeLayout)view.findViewById(R.id.layout_displayhome_ViewEmployee);
        txt_displayhome_ViewAllCategory = (TextView)view.findViewById(R.id.txt_displayhome_ViewAllCategory);
        txt_displayhome_ViewAllStatistic = (TextView)view.findViewById(R.id.txt_displayhome_ViewAllStatistic);

        //Khoi tao ket noi
        categoryDAO = new CategoryDAO(getActivity());
        orderDAO = new OrderDAO(getActivity());

        ShowCategoryList();
        ShowOrderInDay();

        layout_displayhome_Statistic.setOnClickListener(this);
        layout_displayhome_ViewTable.setOnClickListener(this);
        layout_displayhome_ViewMenu.setOnClickListener(this);
        layout_displayhome_ViewEmployee.setOnClickListener(this);
        txt_displayhome_ViewAllCategory.setOnClickListener(this);
        txt_displayhome_ViewAllCategory.setOnClickListener(this);

        return view;
    }

    private void ShowCategoryList(){
        rcv_displayhome_Category.setHasFixedSize(true);
        rcv_displayhome_Category.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryDTOList = categoryDAO.getListCategory();
//        adapterRecycleViewCategory = new AdapterRecycleViewCategory(getActivity(),R.layout.custom_layout_displaycategory,loaiMonDTOList);
//        rcv_displayhome_LoaiMon.setAdapter(adapterRecycleViewCategory);
//        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    private void ShowOrderInDay(){
        rcv_displayhome_OrderInDay.setHasFixedSize(true);
        rcv_displayhome_OrderInDay.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String orderDate = dateFormat.format(calendar.getTime());

        orderDTOList = orderDAO.getListOrderByDate(orderDate);
//        adapterRecycleViewStatistic = new AdapterRecycleViewStatistic(getActivity(),R.layout.custom_layout_displaystatistic,donDatDTOS);
//        rcv_displayhome_DonTrongNgay.setAdapter(adapterRecycleViewStatistic);
//        adapterRecycleViewCategory.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v){
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navigation_view_home);
        switch (id){
            case R.id.layout_displayhome_Statistic:
            case R.id.txt_displayhome_ViewAllStatistic:
                FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
                //tranDisplayStatistic.replace(R.id.contentView, new DisplayStatisticFragment);
                tranDisplayStatistic.addToBackStack(null);
                tranDisplayStatistic.commit();
                navigationView.setCheckedItem(R.id.nav_statistic);

                break;

            case R.id.layout_displayhome_ViewTable:
                FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                //tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                tranDisplayTable.addToBackStack(null);
                tranDisplayTable.commit();
                navigationView.setCheckedItem(R.id.nav_table);

                break;

            case R.id.layout_displayhome_ViewMenu:
//                Intent iAddCategory = new Intent(getActivity(), AddCategoryActivity.class);
//                startActivity(iAddCategory);
//                navigationView.setCheckedItem(R.id.nav_category);

                break;

            case R.id.layout_displayhome_ViewEmployee:
                HomeActivity homeAct= (HomeActivity)getActivity();
                if(homeAct.getAccessId() == 1)
                {
                    FragmentTransaction tranDisplayStaff= getActivity().getSupportFragmentManager().beginTransaction();
                    //tranDisplayStaff.replace(R.id.contentView,new DisplayStaffFragment());
                    tranDisplayStaff.addToBackStack(null);
                    tranDisplayStaff.commit();
                    navigationView.setCheckedItem(R.id.nav_staff);
                }

                break;

            case R.id.txt_displayhome_ViewAllCategory:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                //tranDisplayCategory.replace(R.id.contentView,new DisplayCategoryFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();
                navigationView.setCheckedItem(R.id.nav_category);

                break;
        }
    }

}