package com.example.coffestoreapp.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.coffestoreapp.Activities.AddTableActivity;
import com.example.coffestoreapp.CustomAdapter.AdapterDisplayTable;
import com.example.coffestoreapp.Activities.EditTableActivity;
//Todo: add home activity
import com.example.coffestoreapp.Activities.HomeActivity;
import com.example.coffestoreapp.DAO.TableDAO;
import com.example.coffestoreapp.DTO.TableDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class DisplayTableFragment extends Fragment {
    GridView GVDisplayTable;
    List<TableDTO> tableDTOList;
    TableDAO tableDAO;
    AdapterDisplayTable adapterDisplayTable;

    //Dùng activity result (activityforresult ko hổ trợ nữa) để nhận data gửi từ activity addtable
    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        //Todo: rename ketquathem to check if needed
                        boolean check = intent.getBooleanExtra("ketquathem",false);
                        if(check){
                            ShowTableList();
                            Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> resultLauncherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean check = intent.getBooleanExtra("ketquasua",false);
                        if(check){
                            ShowTableList();
                            Toast.makeText(getActivity(),getResources().getString(R.string.edit_sucessful),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),getResources().getString(R.string.edit_failed),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaytable_layout,container,false);
        setHasOptionsMenu(true);
        //Todo: add home activity
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý bàn");

        GVDisplayTable = (GridView)view.findViewById(R.id.gvDisplayTable);
        tableDAO = new TableDAO(getActivity());

        ShowTableList();

        registerForContextMenu(GVDisplayTable);
        return view;
    }

    //tạo ra context menu khi longclick
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Xử lí cho từng trường hợp trong contextmenu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = menuInfo.position;
        int tableId = tableDTOList.get(pos).getTableID();
        switch(id){
            case R.id.itEdit:
                Intent intent = new Intent(getActivity(), EditTableActivity.class);
                intent.putExtra("tableid",tableId);
                resultLauncherEdit.launch(intent);
                break;

            case R.id.itDelete:
                boolean deleteStatus = tableDAO.deleteTableById(tableId);
                if(deleteStatus){
                    ShowTableList();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed),Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddTable = menu.add(1,R.id.itAddTable,1,R.string.addTable);
        itAddTable.setIcon(R.drawable.ic_baseline_add_24);
        itAddTable.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.itAddTable:
                Intent iAddTable = new Intent(getActivity(), AddTableActivity.class);
                resultLauncherAdd.launch(iAddTable);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterDisplayTable.notifyDataSetChanged();
    }

    private void ShowTableList(){
        tableDTOList = tableDAO.GetAllTables();
        adapterDisplayTable = new AdapterDisplayTable(getActivity(),R.layout.custom_layout_displaytable, tableDTOList);
        GVDisplayTable.setAdapter(adapterDisplayTable);
        adapterDisplayTable.notifyDataSetChanged();
    }
}
