package com.example.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.login.LoginDbHelper;
import com.example.e_commerce.login.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button loginBtn;
    EditText userEmail,userPassword;
    TextView registerLink,forgetLink;
    LoginDbHelper databaseHelper;
    CheckBox checkBoxRememberMe;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink=(TextView) findViewById(R.id.register_link);
        forgetLink=(TextView) findViewById(R.id.forget_link);
        loginBtn = (Button) findViewById(R.id.login_btn);
        userEmail = (EditText) findViewById(R.id.edt_User_email);
        userPassword = (EditText) findViewById(R.id.edt_User_Password);
        checkBoxRememberMe = findViewById(R.id.remember_me);

        databaseHelper = new LoginDbHelper(LoginActivity.this);
        loginBtn.setOnClickListener(this);
        registerLink.setOnClickListener(this);
        forgetLink.setOnClickListener(this);

        sharedPreferences=getSharedPreferences(User.PREFERENCE_NAME, Context.MODE_PRIVATE);


    }
    private void clearFields()
    {
        userEmail.setText(null);
        userPassword.setText(null);
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
                else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                boolean isExist = databaseHelper.isUserExists(userEmail.getText().toString(), userPassword.getText().toString());

                if(isExist)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(User.REMEMBER_ME, checkBoxRememberMe.isChecked());
                    editor.apply();
                    clearFields();
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "noooo", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.forget_link:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                Toast.makeText(LoginActivity.this, "fff", Toast.LENGTH_LONG).show();
                break;

            case R.id.register_link:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

        }
    }
}