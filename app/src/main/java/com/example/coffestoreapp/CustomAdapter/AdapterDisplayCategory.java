package com.example.coffestoreapp.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffestoreapp.DTO.CategoryDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class AdapterDisplayCategory extends BaseAdapter {
    Context context;
    int layout;
    List<CategoryDTO> categoryDTOList ;
    ViewHolder viewHolder;

    //constructor
    public AdapterDisplayCategory(Context context, int layout, List<CategoryDTO> categoryDTOList){
        this.context = context;
        this.layout = layout;
        this.categoryDTOList = categoryDTOList;
    }

    @Override
    public int getCount() {
        return categoryDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryDTOList.get(position).getCategoryID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        //nếu lần đầu gọi view
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            //truyền component vào viewholder để ko gọi findview ở những lần hiển thị khác
            viewHolder.img_customcategory_CategoryPicture = (ImageView)view.findViewById(R.id.img_customcategory_CategoryPicture);
            viewHolder.txt_customcategory_CategoryName = (TextView)view.findViewById(R.id.txt_customcategory_CategoryName);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        CategoryDTO categoryDTO = categoryDTOList.get(position);

        viewHolder.txt_customcategory_CategoryName.setText(categoryDTO.getCategoryName());

        byte[] categoryimage = categoryDTO.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        viewHolder.img_customcategory_CategoryPicture.setImageBitmap(bitmap);

        return view;
    }

    //tạo viewholer lưu trữ component
    public class ViewHolder{
        TextView txt_customcategory_CategoryName;
        ImageView img_customcategory_CategoryPicture;
    }
}
