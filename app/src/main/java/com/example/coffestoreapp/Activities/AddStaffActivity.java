package com.example.coffestoreapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.example.coffestoreapp.DAO.EmployeeDAO;
import com.example.coffestoreapp.DTO.EmployeeDTO;
import com.example.coffestoreapp.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddStaffActivity extends AppCompatActivity implements View.OnClickListener{

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    ImageView IMG_addstaff_back;
    TextView TXT_addstaff_title;
    TextInputLayout TXTL_addstaff_fullName, TXTL_addstaff_userName, TXTL_addstaff_email, TXTL_addstaff_phoneNumber, TXTL_addstaff_password;
    RadioGroup RG_addstaff_gender,rg_addstaff_role;
    RadioButton RD_addstaff_male,RD_addstaff_female,RD_addstaff_other,rd_addstaff_manager,rd_addstaff_employee;
    DatePicker DT_addstaff_dateOfBirth;
    Button BTN_addstaff_addStaff;
    EmployeeDAO employeeDAO;
    String fullName,userName,email,phoneNumber,password,gender,dateOfBirth;
    int staffID = 0,role = 0;
    long check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstaff_layout);

        //region Lấy đối tượng trong view
        TXT_addstaff_title = (TextView)findViewById(R.id.txt_addstaff_title);
        IMG_addstaff_back = (ImageView)findViewById(R.id.img_addstaff_back);
        TXTL_addstaff_fullName = (TextInputLayout)findViewById(R.id.txtl_addstaff_fullName);
        TXTL_addstaff_userName = (TextInputLayout)findViewById(R.id.txtl_addstaff_userName);
        TXTL_addstaff_email = (TextInputLayout)findViewById(R.id.txtl_addstaff_Email);
        TXTL_addstaff_phoneNumber = (TextInputLayout)findViewById(R.id.txtl_addstaff_phoneNumber);
        TXTL_addstaff_password = (TextInputLayout)findViewById(R.id.txtl_addstaff_password);
        RG_addstaff_gender = (RadioGroup)findViewById(R.id.rg_addstaff_gender);
        rg_addstaff_role = (RadioGroup)findViewById(R.id.rg_addstaff_role);
        RD_addstaff_male = (RadioButton)findViewById(R.id.rd_addstaff_male);
        RD_addstaff_female = (RadioButton)findViewById(R.id.rd_addstaff_female);
        RD_addstaff_other = (RadioButton)findViewById(R.id.rd_addstaff_other);
        rd_addstaff_manager = (RadioButton)findViewById(R.id.rd_addstaff_manager);
        rd_addstaff_employee = (RadioButton)findViewById(R.id.rd_addstaff_employee);
        DT_addstaff_dateOfBirth = (DatePicker)findViewById(R.id.dt_addstaff_dateOfBirth);
        BTN_addstaff_addStaff = (Button)findViewById(R.id.btn_addstaff_addStaff);

        //endregion

        employeeDAO = new EmployeeDAO(this);

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        staffID = getIntent().getIntExtra("manv",0);   //lấy manv từ display staff
        if(staffID != 0){
            TXT_addstaff_title.setText("Sửa nhân viên");
            EmployeeDTO employeeDTO = employeeDAO.getEmployeeById(staffID);

            //Hiển thị thông tin từ csdl
            TXTL_addstaff_fullName.getEditText().setText(employeeDTO.getFullName());
            TXTL_addstaff_userName.getEditText().setText(employeeDTO.getUserName());
            TXTL_addstaff_email.getEditText().setText(employeeDTO.getEmail());
            TXTL_addstaff_phoneNumber.getEditText().setText(employeeDTO.getPhoneNumber());
            TXTL_addstaff_password.getEditText().setText(employeeDTO.getPassword());

            //Hiển thị giới tính từ csdl
            String gioitinh = employeeDTO.getGender();
            if(gioitinh.equals("Nam")){
                RD_addstaff_male.setChecked(true);
            }else if (gioitinh.equals("Nữ")){
                RD_addstaff_female.setChecked(true);
            }else {
                RD_addstaff_other.setChecked(true);
            }

            if(employeeDTO.getRoleId() == 1){
                rd_addstaff_manager.setChecked(true);
            }else {
                rd_addstaff_employee.setChecked(true);
            }

            //Hiển thị ngày sinh từ csdl
            String date = employeeDTO.getBirthday();
            String[] items = date.split("/");
            int day = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]) - 1;
            int year = Integer.parseInt(items[2]);
            DT_addstaff_dateOfBirth.updateDate(year,month,day);
            BTN_addstaff_addStaff.setText("Sửa nhân viên");
        }
        //endregion

        BTN_addstaff_addStaff.setOnClickListener(this);
        IMG_addstaff_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String chucnang;
        switch (id){
            case R.id.btn_addstaff_addStaff:
                if( !validateAge() | !validateEmail() | !validateFullName() | !validateGender() | !validatePassWord() |
                !validatePermission() | !validatePhone() | !validateUserName()){
                    return;
                }
                //Lấy dữ liệu từ view
                fullName = TXTL_addstaff_fullName.getEditText().getText().toString();
                userName = TXTL_addstaff_userName.getEditText().getText().toString();
                email = TXTL_addstaff_email.getEditText().getText().toString();
                phoneNumber = TXTL_addstaff_phoneNumber.getEditText().getText().toString();
                password = TXTL_addstaff_password.getEditText().getText().toString();

                switch (RG_addstaff_gender.getCheckedRadioButtonId()){
                    case R.id.rd_addstaff_male: gender = "Nam"; break;
                    case R.id.rd_addstaff_female: gender = "Nữ"; break;
                    case R.id.rd_addstaff_other: gender = "Khác"; break;
                }
                switch (rg_addstaff_role.getCheckedRadioButtonId()){
                    case R.id.rd_addstaff_manager: role = 1; break;
                    case R.id.rd_addstaff_employee: role = 2; break;
                }

                dateOfBirth = DT_addstaff_dateOfBirth.getDayOfMonth() + "/" + (DT_addstaff_dateOfBirth.getMonth() + 1)
                        +"/"+DT_addstaff_dateOfBirth.getYear();

                //truyền dữ liệu vào obj employeeDTO
                EmployeeDTO employeeDTO = new EmployeeDTO();
                employeeDTO.setFullName(fullName);
                employeeDTO.setUserName(userName);
                employeeDTO.setEmail(email);
                employeeDTO.setPhoneNumber(phoneNumber);
                employeeDTO.setPassword(password);
                employeeDTO.setGender(gender);
                employeeDTO.setBirthday(dateOfBirth);
                employeeDTO.setRoleId(role);

                if(staffID != 0){
                    check = employeeDAO.editEmployee(employeeDTO,staffID);
                    chucnang = "sua";
                }else {
                    check = employeeDAO.addEmployee(employeeDTO);
                    chucnang = "themnv";
                }
                //Thêm, sửa nv dựa theo obj employeeDTO
                Intent intent = new Intent();
                intent.putExtra("ketquaktra",check);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.img_addstaff_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }

    //region validate fields
    private boolean validateFullName(){
        String val = TXTL_addstaff_fullName.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addstaff_fullName.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addstaff_fullName.setError(null);
            TXTL_addstaff_fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = TXTL_addstaff_userName.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            TXTL_addstaff_userName.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            TXTL_addstaff_userName.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addstaff_userName.setError("Không được cách chữ!");
            return false;
        }
        else {
            TXTL_addstaff_userName.setError(null);
            TXTL_addstaff_userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = TXTL_addstaff_email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            TXTL_addstaff_email.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addstaff_email.setError("Email không hợp lệ!");
            return false;
        }
        else {
            TXTL_addstaff_email.setError(null);
            TXTL_addstaff_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = TXTL_addstaff_phoneNumber.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            TXTL_addstaff_phoneNumber.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            TXTL_addstaff_phoneNumber.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            TXTL_addstaff_phoneNumber.setError(null);
            TXTL_addstaff_phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_addstaff_password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addstaff_password.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_addstaff_password.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            TXTL_addstaff_password.setError(null);
            TXTL_addstaff_password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender(){
        if(RG_addstaff_gender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validatePermission(){
        if(rg_addstaff_role.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn quyền",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = DT_addstaff_dateOfBirth.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion

}