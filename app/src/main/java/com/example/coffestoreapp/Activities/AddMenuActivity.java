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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coffestoreapp.DAO.DrinkDAO;
import com.example.coffestoreapp.DTO.DrinkDTO;
import com.example.coffestoreapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button BTN_addmenu_addDrink;
    RelativeLayout layout_statusDrink;
    ImageView IMG_addmenu_addImage, IMG_addmenu_back;
    TextView TXT_addmenu_title;
    TextInputLayout txtl_addmenu_drinkName, txtl_addmenu_price, TXTL_addmenu_category;
    RadioGroup RG_addmenu_status;
    RadioButton RD_addmenu_inStock, RD_addmenu_outOfStock;
    DrinkDAO drinkDAO;
    String categoryName, drinkName, price, status;
    Bitmap bitmapold;
    int categoryId;
    int drinkId = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addmenu_addImage.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmenu_layout);

        // region Lấy đối tượng view
        IMG_addmenu_addImage = (ImageView) findViewById(R.id.img_addmenu_ThemHinh);
        IMG_addmenu_addImage.setTag(R.drawable.drinkfood);
        IMG_addmenu_back = (ImageView) findViewById(R.id.img_addmenu_back);
        txtl_addmenu_drinkName = (TextInputLayout) findViewById(R.id.txtl_addmenu_drinkName);
        txtl_addmenu_price = (TextInputLayout) findViewById(R.id.txtl_addmenu_price);
        TXTL_addmenu_category = (TextInputLayout) findViewById(R.id.txtl_addmenu_category);
        BTN_addmenu_addDrink = (Button) findViewById(R.id.btn_addmenu_addDrink);
        TXT_addmenu_title = (TextView) findViewById(R.id.txt_addmenu_title);
        layout_statusDrink = (RelativeLayout) findViewById(R.id.layout_statusDrink);
        RG_addmenu_status = (RadioGroup) findViewById(R.id.rg_addmenu_status);
        RD_addmenu_inStock = (RadioButton) findViewById(R.id.rd_addmenu_inStock);
        RD_addmenu_outOfStock = (RadioButton) findViewById(R.id.rd_addmenu_outOfStock);
        // endregion

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("maLoai", -1);
        categoryName = intent.getStringExtra("tenLoai");
        drinkDAO = new DrinkDAO(this); // khởi tạo đối tượng dao kết nối csdl
        TXTL_addmenu_category.getEditText().setText(categoryName);

        BitmapDrawable olddrawable = (BitmapDrawable) IMG_addmenu_addImage.getDrawable();
        bitmapold = olddrawable.getBitmap();

        // region Hiển thị trang sửa nếu được chọn từ context menu sửa
        drinkId = getIntent().getIntExtra("drinkId", 0);
        if (drinkId != 0) {
            TXT_addmenu_title.setText("Sửa thực đơn");
            DrinkDTO drinkDTO = drinkDAO.getDrinkById(drinkId);

            txtl_addmenu_drinkName.getEditText().setText(drinkDTO.getDrinkName());
            txtl_addmenu_price.getEditText().setText(drinkDTO.getPrice());

            byte[] menuimage = drinkDTO.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage, 0, menuimage.length);
            IMG_addmenu_addImage.setImageBitmap(bitmap);

            layout_statusDrink.setVisibility(View.VISIBLE);
            String tinhtrang = drinkDTO.getStatus();
            if (tinhtrang.equals("true")) {
                RD_addmenu_inStock.setChecked(true);
            } else {
                RD_addmenu_outOfStock.setChecked(true);
            }

            BTN_addmenu_addDrink.setText("Sửa món");
        }

        // endregion

        IMG_addmenu_addImage.setOnClickListener(this);
        BTN_addmenu_addDrink.setOnClickListener(this);
        IMG_addmenu_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id) {
            case R.id.img_addmenu_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG
                        .launch(Intent.createChooser(iGetIMG, getResources().getString(R.string.choseimg)));
                break;

            case R.id.img_addmenu_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.btn_addmenu_addDrink:
                // ktra validation
                if (!validateImage() | !validateName() | !validatePrice()) {
                    return;
                }

                drinkName = txtl_addmenu_drinkName.getEditText().getText().toString();
                price = txtl_addmenu_price.getEditText().getText().toString();
                switch (RG_addmenu_status.getCheckedRadioButtonId()) {
                    case R.id.rd_addmenu_inStock:
                        status = "true";
                        break;
                    case R.id.rd_addmenu_outOfStock:
                        status = "false";
                        break;
                }

                DrinkDTO drinkDTO = new DrinkDTO();
                drinkDTO.setCategoryID(categoryId);
                drinkDTO.setDrinkName(drinkName);
                drinkDTO.setPrice(price);
                drinkDTO.setStatus(status);
                drinkDTO.setImage(imageViewtoByte(IMG_addmenu_addImage));
                if (drinkId != 0) {
                    ktra = drinkDAO.editDrink(drinkDTO, drinkId);
                    chucnang = "suamon";
                } else {
                    ktra = drinkDAO.addDrink(drinkDTO);
                    chucnang = "themmon";
                }

                // Thêm, sửa món dựa theo obj loaimonDTO
                Intent intent = new Intent();
                intent.putExtra("ktra", ktra);
                intent.putExtra("chucnang", chucnang);
                setResult(RESULT_OK, intent);
                finish();

                break;
        }
    }

    // Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    // region Validate field
    private boolean validateImage() {
        BitmapDrawable drawable = (BitmapDrawable) IMG_addmenu_addImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if (bitmap == bitmapold) {
            Toast.makeText(getApplicationContext(), "Xin chọn hình ảnh", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateName() {
        String val = txtl_addmenu_drinkName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            txtl_addmenu_drinkName.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            txtl_addmenu_drinkName.setError(null);
            txtl_addmenu_drinkName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice() {
        String val = txtl_addmenu_price.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            txtl_addmenu_price.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (!val.matches(("\\d+(?:\\.\\d+)?"))) {
            txtl_addmenu_price.setError("Giá tiền không hợp lệ");
            return false;
        } else {
            txtl_addmenu_price.setError(null);
            txtl_addmenu_price.setErrorEnabled(false);
            return true;
        }
    }
    // endregion

}