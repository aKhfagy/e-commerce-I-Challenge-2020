package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.login.LoginDbHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button loginBtn;
    EditText userEmail,userPassword;
    TextView registerLink;
    LoginDbHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink=(TextView) findViewById(R.id.register_link);
        loginBtn = (Button) findViewById(R.id.login_btn);
        userEmail = (EditText) findViewById(R.id.edt_User_email);
        userPassword = (EditText) findViewById(R.id.edt_User_Password);

        databaseHelper = new LoginDbHelper(LoginActivity.this);

        loginBtn.setOnClickListener(this);
        registerLink.setOnClickListener(this);



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
        MainActivity.loggedIn = true;
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
                }
                boolean isExist = databaseHelper.isUserExists(userEmail.getText().toString(), userPassword.getText().toString());

                if(isExist)
                {
                    Toast.makeText(LoginActivity.this, "Loggggggggg", Toast.LENGTH_LONG).show();
                    clearFields();
                }
                else {
                    Toast.makeText(LoginActivity.this, "noooo", Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.register_link:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }
}