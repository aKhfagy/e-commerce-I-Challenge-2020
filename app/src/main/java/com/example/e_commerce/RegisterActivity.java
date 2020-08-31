package com.example.e_commerce;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.login.LoginDbHelper;
import com.example.e_commerce.login.User;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button registerBtn;
    EditText userName,userEmail,userPassword,userConfirmPassword;
    TextView loginLink,birthdayLink;
    LoginDbHelper databaseHelper;
    DatePickerDialog datePickerDialog;
    User user = new User();
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginLink = findViewById(R.id.login_link);
        birthdayLink = findViewById(R.id.birthday_link);
        registerBtn = findViewById(R.id.register_btn);
        userName = findViewById(R.id.edt_User_name);
        userEmail = findViewById(R.id.edt_User_email);
        userPassword = findViewById(R.id.edt_User_Password);
        userConfirmPassword = findViewById(R.id.edt_User_Confirm_Password);

        databaseHelper = new LoginDbHelper(RegisterActivity.this);

        registerBtn.setOnClickListener(this);
        loginLink.setOnClickListener(this);
        birthdayLink.setOnClickListener(this);
        setDateListener();

    }
    private void clearFields()
    {
        userName.setText(null);
        userEmail.setText(null);
        userPassword.setText(null);
        userConfirmPassword.setText(null);
        birthdayLink.setText("Choose your birthday");

    }
    private boolean inputValedation(EditText userName,EditText userEmail,EditText userPassword,EditText userConfirmPassword)
    {
        // Empty fields
        if(userName.getText().toString().isEmpty())
        {
            Toast.makeText(RegisterActivity.this, "Please enter your name ", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userEmail.getText().toString().isEmpty())
        {
            Toast.makeText(RegisterActivity.this, "Please enter your email ", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userPassword.getText().toString().isEmpty())
        {
            Toast.makeText(RegisterActivity.this, "Please enter your password ", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userConfirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(RegisterActivity.this, "Please enter your confirmation password ", Toast.LENGTH_LONG).show();
            return false;
        }
        if(birthdayLink.getText().toString().equals("Choose your birthday"))
        {
            Toast.makeText(RegisterActivity.this, "Please enter your birthdate ", Toast.LENGTH_LONG).show();
            return false;
        }
        //
        if( !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches())
        {
            Toast.makeText(RegisterActivity.this, "Please enter correct email format", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!userPassword.getText().toString().equals(userConfirmPassword.getText              ().toString()))
        {
            Toast.makeText(RegisterActivity.this, "Please confirm your password correctly ", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    public void createCalenderBox()
    {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener, year,month,day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }
    public void setDateListener()
    {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                birthdayLink.setText(date);
            }
        };
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_btn:
                if(!inputValedation(userName,userEmail,userPassword,userConfirmPassword))
                    break;
                user.setUsername(userName.getText().toString());
                user.setUserEmail(userEmail.getText().toString());
                user.setPassword(userPassword.getText().toString());
                user.setBirthdate(birthdayLink.getText().toString());

                boolean isExist = databaseHelper.isUserExists(user.getUserEmail(), user.getPassword(),false);
                if (isExist) {
                    Toast.makeText(RegisterActivity.this, "existssssssss.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("username", userEmail.getText().toString());
                    startActivity(intent);

                } else {
                    databaseHelper.addUser(user);
                    clearFields();
                    Toast.makeText(RegisterActivity.this, "addedddddddddddd", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case R.id.birthday_link:
                createCalenderBox();

                break;
            case R.id.login_link:
                finish();
                break;
        }
    }
}