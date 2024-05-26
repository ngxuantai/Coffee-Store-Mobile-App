package com.example.coffestoreapp.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.coffestoreapp.R;

import java.util.List;
import com.example.coffestoreapp.DTO.PaymentDTO;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDisplayPayment extends BaseAdapter {
    Context context;
    int layout;
    List<PaymentDTO> paymentDTOList;
    ViewHolder viewHolder;

    public AdapterDisplayPayment(Context context, int layout, List<PaymentDTO> paymentDTOList){
        this.context = context;
        this.layout = layout;
        this.paymentDTOList = paymentDTOList;
    }

    @Override
    public int getCount() {
        return paymentDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_custompayment_productImage = (CircleImageView)view.findViewById(R.id.img_custompayment_productImage);
            viewHolder.txt_custompayment_productName = (TextView)view.findViewById(R.id.txt_custompayment_productName);
            viewHolder.txt_custompayment_quantity = (TextView)view.findViewById(R.id.txt_custompayment_quantity);
            viewHolder.txt_custompayment_price = (TextView)view.findViewById(R.id.txt_custompayment_price);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        PaymentDTO paymentDTO = paymentDTOList.get(position);

        viewHolder.txt_custompayment_productName.setText(paymentDTO.getDrinkName());
        viewHolder.txt_custompayment_quantity.setText(String.valueOf(paymentDTO.getQuantity()));
        viewHolder.txt_custompayment_price.setText(String.valueOf(paymentDTO.getPrice())+" đ");

        byte[] paymentimg = paymentDTO.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(paymentimg,0,paymentimg.length);
        viewHolder.img_custompayment_productImage.setImageBitmap(bitmap);

        return view;
    }

    public class ViewHolder{
        CircleImageView img_custompayment_productImage;
        TextView txt_custompayment_productName, txt_custompayment_quantity, txt_custompayment_price;
    }
}
