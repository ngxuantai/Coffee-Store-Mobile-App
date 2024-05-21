package com.example.coffestoreapp.Activities;

import static com.example.coffestoreapp.R.*;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ImageView IMG_signup_back;
    Button BTN_signup_next;
    TextView TXT_signup_title;
    TextInputLayout TXTL_signup_fullName, TXTL_signup_userName, TXTL_signup_email, TXTL_signup_phoneNumber, TXTL_signup_password;
    public static final String BUNDLE = "BUNDLE";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.register_layout);

        IMG_signup_back = (ImageView)findViewById(id.img_signup_back);
        BTN_signup_next = (Button)findViewById(id.btn_signup_next);
        TXT_signup_title = (TextView)findViewById(id.txt_signup_title);
        TXTL_signup_fullName = (TextInputLayout)findViewById(id.txtl_signup_fullName);
        TXTL_signup_userName = (TextInputLayout)findViewById(id.txtl_signup_userName);
        TXTL_signup_email = (TextInputLayout)findViewById(id.txtl_signup_email);
        TXTL_signup_phoneNumber = (TextInputLayout)findViewById(id.txtl_signup_phoneNumber);
        TXTL_signup_password = (TextInputLayout)findViewById(id.txtl_signup_password);

        BTN_signup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateFullName() | !validateUserName() | !validateemail() | !validatePhone() | !validatePassWord()){
                    return;
                }
                String fullName = TXTL_signup_fullName.getEditText().getText().toString();
                String userName = TXTL_signup_userName.getEditText().getText().toString();
                String email = TXTL_signup_email.getEditText().getText().toString();
                String phoneNumber = TXTL_signup_phoneNumber.getEditText().getText().toString();
                String password = TXTL_signup_password.getEditText().getText().toString();

                byBundleNextSignupScreen(fullName, userName, email, phoneNumber, password);
            }
        });

    }

    public void backFromRegister(View view){

        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(id.layoutRegister),"transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }
    }

    //truyền dữ liệu qua trang đk thứ 2 bằng bundle
    public void byBundleNextSignupScreen(String fullName, String userName, String email, String phoneNumber, String password){

        Intent intent = new Intent(getApplicationContext(),Register2ndActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fullName", fullName);
        bundle.putString("username", userName);
        bundle.putString("email", email);
        bundle.putString("phoneNumber", phoneNumber);
        bundle.putString("password", password);
        intent.putExtra(BUNDLE, bundle);

        startActivity(intent);
        overridePendingTransition(anim.fade_in, anim.fade_out);
    }

    //region Validate field
    private boolean validateFullName(){
        String val = TXTL_signup_fullName.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_signup_fullName.setError(getResources().getString(string.not_empty));
            return false;
        }else {
            TXTL_signup_fullName.setError(null);
            TXTL_signup_fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = TXTL_signup_userName.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            TXTL_signup_userName.setError(getResources().getString(string.not_empty));
            return false;
        }else if(val.length()>50){
            TXTL_signup_userName.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_signup_userName.setError("Không được cách chữ!");
            return false;
        }
        else {
            TXTL_signup_userName.setError(null);
            TXTL_signup_userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateemail(){
        String val = TXTL_signup_email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            TXTL_signup_email.setError(getResources().getString(string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_signup_email.setError("email không hợp lệ!");
            return false;
        }
        else {
            TXTL_signup_email.setError(null);
            TXTL_signup_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = TXTL_signup_phoneNumber.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            TXTL_signup_phoneNumber.setError(getResources().getString(string.not_empty));
            return false;
        }else if(val.length() != 10){
            TXTL_signup_phoneNumber.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            TXTL_signup_phoneNumber.setError(null);
            TXTL_signup_phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_signup_password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_signup_password.setError(getResources().getString(string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_signup_password.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            TXTL_signup_password.setError(null);
            TXTL_signup_password.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}