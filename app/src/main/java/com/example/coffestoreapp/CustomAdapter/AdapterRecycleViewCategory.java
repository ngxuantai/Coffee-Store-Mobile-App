package com.example.coffestoreapp.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffestoreapp.DTO.CategoryDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class AdapterRecycleViewCategory extends RecyclerView.Adapter<AdapterRecycleViewCategory.ViewHolder> {
    Context context;
    int layout;
    List<CategoryDTO> CategoryDTOList;

    public AdapterRecycleViewCategory(Context context,int layout, List<CategoryDTO> CategoryDTOList){
        this.context = context;
        this.layout = layout;
        this.CategoryDTOList = CategoryDTOList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CategoryDTO CategoryDTO = CategoryDTOList.get(position);
        holder.txt_customcategory_CategoryName.setText(CategoryDTO.getCategoryName());
        byte[] categoryimage = CategoryDTO.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        holder.img_customcategory_CategoryPicture.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return CategoryDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_customcategory_CategoryName;
        ImageView img_customcategory_CategoryPicture;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customcategory_CategoryName = itemView.findViewById(R.id.txt_customcategory_CategoryName);
            img_customcategory_CategoryPicture = itemView.findViewById(R.id.img_customcategory_CategoryPicture);
        }
    }
}
