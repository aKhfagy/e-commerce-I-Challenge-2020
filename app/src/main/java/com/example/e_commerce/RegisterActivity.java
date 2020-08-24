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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button registerBtn;
    EditText userName,userEmail,userPassword,userConfirmPassword;
    TextView loginLink;
    LoginDbHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginLink=(TextView) findViewById(R.id.login_link);
        registerBtn = (Button) findViewById(R.id.register_btn);
        userName = (EditText) findViewById(R.id.edt_User_name);
        userEmail = (EditText) findViewById(R.id.edt_User_email);
        userPassword = (EditText) findViewById(R.id.edt_User_Password);
        userConfirmPassword = (EditText) findViewById(R.id.edt_User_Confirm_Password);

        databaseHelper = new LoginDbHelper(RegisterActivity.this);
        registerBtn.setOnClickListener(this);
        loginLink.setOnClickListener(this);

    }
    private void clearFields()
    {
        userEmail.setText(null);
        userPassword.setText(null);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_btn:
                if(!inputValedation(userName,userEmail,userPassword,userConfirmPassword))
                    break;
                boolean isExist = databaseHelper.isUserExists(userEmail.getText().toString(), userPassword.getText().toString());
                if (isExist) {
                    Toast.makeText(RegisterActivity.this, "existssssssss.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("username", userEmail.getText().toString());
                    startActivity(intent);

                } else {
                    databaseHelper.addUser(userName.getText().toString(),userEmail.getText().toString(), userPassword.getText().toString());
                    clearFields();
                    Toast.makeText(RegisterActivity.this, "addedddddddddddd", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.login_link:
                finish();
                break;
        }
    }
}