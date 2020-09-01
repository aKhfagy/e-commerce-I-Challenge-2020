package com.example.e_commerce.login;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;

import java.util.Calendar;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button registerBtn;
    EditText userName,userEmail,userPassword,userConfirmPassword;
    TextView loginLink,birthdayTxt;
    ImageView birthdayLink;
    UserDbHelper databaseHelper;
    DatePickerDialog datePickerDialog;
    User user = new User();
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginLink = findViewById(R.id.login_link);
        birthdayLink = findViewById(R.id.birthday_link);
        birthdayTxt = findViewById(R.id.birthday_txt);
        registerBtn = findViewById(R.id.register_btn);
        userName = findViewById(R.id.edt_User_name);
        userEmail = findViewById(R.id.edt_User_email);
        userPassword = findViewById(R.id.edt_User_Password);
        userConfirmPassword = findViewById(R.id.edt_User_Confirm_Password);

        databaseHelper = new UserDbHelper(RegisterActivity.this);

        registerBtn.setOnClickListener(this);
        loginLink.setOnClickListener(this);
        birthdayLink.setOnClickListener(this);
        setDateListener();

    }
    @SuppressLint("SetTextI18n")
    private void clearFields()
    {
        userName.setText(null);
        userEmail.setText(null);
        userPassword.setText(null);
        userConfirmPassword.setText(null);
        birthdayTxt.setText("Choose your birthday");

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
        if(birthdayTxt.getText().toString().equals("Choose your birthday"))
        {
            Toast.makeText(RegisterActivity.this, "Please enter your birthdate ", Toast.LENGTH_LONG).show();
            return false;
        }
        // Correct format
        if( !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches())
        {
            Toast.makeText(RegisterActivity.this, "Please enter correct email format", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!userPassword.getText().toString().equals(userConfirmPassword.getText().toString()))
        {
            Toast.makeText(RegisterActivity.this, "Please confirm your password correctly ", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createCalenderBox()
    {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener, year,month,day);
        Objects.requireNonNull(datePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }
    public void setDateListener()
    {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                birthdayTxt.setText(date);
            }
        };
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_btn:
                if(!inputValedation(userName,userEmail,userPassword,userConfirmPassword))
                    break;
                user.setUsername(userName.getText().toString());
                user.setUserEmail(userEmail.getText().toString());
                user.setPassword(userPassword.getText().toString());
                user.setBirthdate(birthdayTxt.getText().toString());
                int isExist = databaseHelper.isUserExists(user.getUserEmail(), user.getPassword());
                if (isExist!=-1) {
                    Toast.makeText(RegisterActivity.this, "Exists.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                 else {
                    databaseHelper.addUser(user);
                    clearFields();
                    Toast.makeText(RegisterActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
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