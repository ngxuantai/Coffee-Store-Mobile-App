package com.example.coffestoreapp.CustomAdapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffestoreapp.DAO.RoleDAO;
import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class AdapterDisplayStaff extends BaseAdapter {
    Context context;
    int layout;
    List<EmployeeDTO> employeeDTOS;
    ViewHolder viewHolder;
    RoleDAO roleDAO;

    public AdapterDisplayStaff(Context context, int layout, List<EmployeeDTO> employeeDTOS){
        this.context = context;
        this.layout = layout;
        this.employeeDTOS = employeeDTOS;
        roleDAO = new RoleDAO(context);
    }

    @Override
    public int getCount() {
        return employeeDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employeeDTOS.get(position).getEmployId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_customstaff_staffImage = (ImageView)view.findViewById(R.id.img_customstaff_staffImage);
            viewHolder.txt_customstaff_staffName = (TextView)view.findViewById(R.id.txt_customstaff_staffName);
            viewHolder.txt_customstaff_role = (TextView)view.findViewById(R.id.txt_customstaff_role);
            viewHolder.txt_customstaff_phoneNumber = (TextView)view.findViewById(R.id.txt_customstaff_phoneNumber);
            viewHolder.txt_customstaff_Email = (TextView)view.findViewById(R.id.txt_customstaff_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        EmployeeDTO employeeDTO = employeeDTOS.get(position);

        viewHolder.txt_customstaff_staffName.setText(employeeDTO.getFullName());
        viewHolder.txt_customstaff_role.setText(roleDAO.getRoleById(employeeDTO.getRoleId()));
        viewHolder.txt_customstaff_phoneNumber.setText(employeeDTO.getPhoneNumber());
        viewHolder.txt_customstaff_Email.setText(employeeDTO.getEmail());

        return view;
    }

    public class ViewHolder{
        ImageView img_customstaff_staffImage;
        TextView txt_customstaff_staffName, txt_customstaff_role,txt_customstaff_phoneNumber, txt_customstaff_Email;
    }
}
