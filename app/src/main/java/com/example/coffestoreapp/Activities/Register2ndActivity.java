package com.example.coffestoreapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffestoreapp.DAO.EmployeeDAO;
import com.example.coffestoreapp.DAO.RoleDAO;
import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.R;

import java.util.Calendar;

public class Register2ndActivity extends AppCompatActivity {

    RadioGroup RG_signup_gender;
    DatePicker DT_signup_birthday;
    Button BTN_signup_next;
    String fullName,username,eMail,phoneNumber,password,gender;
    EmployeeDAO employeeDAO;
    RoleDAO roleDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2nd_layout);

        RG_signup_gender = (RadioGroup)findViewById(R.id.rg_signup_gender);
        DT_signup_birthday = (DatePicker)findViewById(R.id.dt_signup_birthday);
        BTN_signup_next = (Button)findViewById(R.id.btn_signup_next);

        Bundle bundle = getIntent().getBundleExtra(com.example.coffestoreapp.Activities.RegisterActivity.BUNDLE);
        if(bundle != null) {
            fullName = bundle.getString("fullName");
            username = bundle.getString("username");
            eMail = bundle.getString("email");
            phoneNumber = bundle.getString("phoneNumber");
            password = bundle.getString("password");
        }
        employeeDAO = new EmployeeDAO(this);
        roleDAO = new RoleDAO(this);

        BTN_signup_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAge() | !validateGender()){
                    return;
                }

                switch (RG_signup_gender.getCheckedRadioButtonId()){
                    case R.id.rd_signup_male:
                        gender = "Nam"; break;
                    case R.id.rd_signup_female:
                        gender = "Nữ"; break;
                    case R.id.rd_signup_other:
                        gender = "Khác"; break;
                }
                String birthday = DT_signup_birthday.getDayOfMonth() + "/" + (DT_signup_birthday.getMonth() + 1)
                        +"/"+DT_signup_birthday.getYear();

                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setFullName(fullName);
                employeeDTO.setUserName(username);
                employeeDTO.setEmail(eMail);
                employeeDTO.setPhoneNumber(phoneNumber);
                employeeDTO.setPassword(password);
                employeeDTO.setGender(gender);
                employeeDTO.setBirthday(birthday);

                if(!employeeDAO.checkExistEmployee()){
                    roleDAO.addRole("Quản lý");
                    roleDAO.addRole("Nhân viên");
                    employeeDTO.setRoleId(1);
                }else {
                    employeeDTO.setRoleId(2);
                }

                long ktra = employeeDAO.addEmployee(employeeDTO);
                if(ktra != 0){
                    Toast.makeText(Register2ndActivity.this,getResources().getString(R.string.add_success),Toast.LENGTH_SHORT).show();
                    callLoginFromRegister();
                }else{
                    Toast.makeText(Register2ndActivity.this,getResources().getString(R.string.add_fail),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void backFromRegister2nd(View view){
        Intent intent = new Intent(getApplicationContext(), com.example.coffestoreapp.Activities.RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void callLoginFromRegister(){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private boolean validateGender(){
        if(RG_signup_gender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = DT_signup_birthday.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 16){
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion
}