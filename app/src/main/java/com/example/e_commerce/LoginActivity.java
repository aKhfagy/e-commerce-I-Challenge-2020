package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.login.LoginDbHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button loginBtn;
    EditText userEmail,userPassword;
    TextView registerLink,forgetLink;
    LoginDbHelper databaseHelper;
    CheckBox checkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink = findViewById(R.id.register_link);
        forgetLink = findViewById(R.id.forget_link);
        loginBtn = findViewById(R.id.login_btn);
        userEmail = findViewById(R.id.edt_User_email);
        userPassword = findViewById(R.id.edt_User_Password);
        checkBoxRememberMe = findViewById(R.id.remember_me);

        databaseHelper = new LoginDbHelper(LoginActivity.this);
        loginBtn.setOnClickListener(this);
        registerLink.setOnClickListener(this);
        forgetLink.setOnClickListener(this);


    }
    private void clearFields()
    {
        userEmail.setText(null);
        userPassword.setText(null);
        checkBoxRememberMe.setChecked(false);
    }
    private boolean inputValidation(EditText userEmail, EditText userPassword)
    {

        if(userEmail.getText().toString().isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Please enter your email ", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userPassword.getText().toString().isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Please enter your password ", Toast.LENGTH_LONG).show();
            return false;
        }

        if( !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches())
        {
            Toast.makeText(LoginActivity.this, "Please enter correct email format", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if(!inputValidation(userEmail,userPassword))
                    break;
                boolean isExist = databaseHelper.isUserExists(userEmail.getText().toString(), userPassword.getText().toString(),checkBoxRememberMe.isChecked());
                if(isExist)
                {
                    clearFields();
                    startActivity(new Intent(LoginActivity.this, ChooseProductActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Incorrect", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.forget_link:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;

            case R.id.register_link:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }
}