package com.example.coffestoreapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffestoreapp.DAO.CategoryDAO;
import com.example.coffestoreapp.DTO.CategoryDTO;
import com.example.coffestoreapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button BTN_addcategory_CreateCategory;
    ImageView IMG_addcategory_back, IMG_addcategory_AddCategoryPicture;
    TextView TXT_addcategory_title;
    TextInputLayout TXTL_addcategory_CategoryName;
    CategoryDAO categoryDAO;
    int categoryId = 0;
    Bitmap bitmapold;   //Bitmap dạng ảnh theo ma trận các pixel

    //dùng result launcher do activityforresult ko dùng đc nữa
    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addcategory_AddCategoryPicture.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcategory_layout);

        categoryDAO = new CategoryDAO(this);  //khởi tạo đối tượng dao kết nối csdl

        //region Lấy đối tượng view
        BTN_addcategory_CreateCategory = (Button)findViewById(R.id.btn_addcategory_CreateCategory);
        TXTL_addcategory_CategoryName = (TextInputLayout)findViewById(R.id.txtl_addcategory_CategoryName);
        IMG_addcategory_back = (ImageView)findViewById(R.id.img_addcategory_back);
        IMG_addcategory_AddCategoryPicture = (ImageView)findViewById(R.id.img_addcategory_AddCategoryPicture);
        TXT_addcategory_title = (TextView)findViewById(R.id.txt_addcategory_title);
        //endregion

        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addcategory_AddCategoryPicture.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        categoryId = getIntent().getIntExtra("categoryId",0);
        if(categoryId != 0){
            TXT_addcategory_title.setText(getResources().getString(R.string.editcategory));
            CategoryDTO categoryDTO = categoryDAO.getCategoryById(categoryId);

            //Hiển thị lại thông tin từ csdl
            TXTL_addcategory_CategoryName.getEditText().setText(categoryDTO.getCategoryName());

            byte[] categoryimage = categoryDTO.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
            IMG_addcategory_AddCategoryPicture.setImageBitmap(bitmap);

            BTN_addcategory_CreateCategory.setText("Sửa loại");
        }
        //endregion

        IMG_addcategory_back.setOnClickListener(this);
        IMG_addcategory_AddCategoryPicture.setOnClickListener(this);
        BTN_addcategory_CreateCategory.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean check;
        String function;
        switch (id){
            case R.id.img_addcategory_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right); //animation
                break;

            case R.id.img_addcategory_AddCategoryPicture:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*"); //lấy những mục chứa hình ảnh
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);   //lấy mục hiện tại đang chứa hình
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));    //mở intent chọn hình ảnh
                break;

            case R.id.btn_addcategory_CreateCategory:
                if(!validateImage() | !validateName()){
                    return;
                }

                String categoryName = TXTL_addcategory_CategoryName.getEditText().getText().toString();
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategoryName(categoryName);
                categoryDTO.setImage(imageViewtoByte(IMG_addcategory_AddCategoryPicture));
                if(categoryId != 0){
                    check = categoryDAO.editCategory(categoryDTO,categoryId);
                    function = "editCategory";
                }else {
                    check = categoryDAO.addCategory(categoryDTO);
                    function = "addCategory";
                }

                //Thêm, sửa loại dựa theo obj loaimonDTO
                Intent intent = new Intent();
                intent.putExtra("check", check);
                intent.putExtra("function", function);
                setResult(RESULT_OK,intent);
                finish();
                break;

        }
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region validate fields
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addcategory_AddCategoryPicture.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addcategory_CategoryName.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addcategory_CategoryName.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addcategory_CategoryName.setError(null);
            TXTL_addcategory_CategoryName.setErrorEnabled(false);
            return true;
        }
    }
    //endregion

}