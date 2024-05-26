package com.example.coffestoreapp.Activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffestoreapp.DAO.EmployeeDAO;
import com.example.coffestoreapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    Button BTN_login, BTN_login_DangKy;
    TextInputLayout TXTL_login_userName, TXTL_login_password;
    EmployeeDAO employeeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        TXTL_login_userName = (TextInputLayout)findViewById(R.id.txtl_login_username);
        TXTL_login_password = (TextInputLayout)findViewById(R.id.txtl_login_password);
        BTN_login = (Button)findViewById(R.id.btn_login_signIn);
        //BTN_login_DangKy = (Button)findViewById(R.id.btn_login_DangKy);

        employeeDAO = new EmployeeDAO(this);

        BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() | !validatePassWord()){
                    return;
                }

                String userName = TXTL_login_userName.getEditText().getText().toString();
                String password = TXTL_login_password.getEditText().getText().toString();
                int ktra = employeeDAO.checkAuth(userName, password);
                int maquyen = employeeDAO.getRoleEmployee(ktra);
                if(ktra != 0){
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("maquyen",maquyen);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("userName",TXTL_login_userName.getEditText().getText().toString());
                    intent.putExtra("manv",ktra);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void backFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

//    public void callRegisterFromLogin(View view)
//    {
//        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//    }

    //region Validate field
    private boolean validateUserName(){
        String val = TXTL_login_userName.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_userName.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_login_userName.setError(null);
            TXTL_login_userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_login_password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_password.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            TXTL_login_password.setError(null);
            TXTL_login_password.setErrorEnabled(false);
            return true;
        }
    }
}
