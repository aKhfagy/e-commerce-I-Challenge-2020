package com.example.e_commerce.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener  {

    Button SaveBtn;
    EditText userEmail, newPassword, ConfirmNewPassword;
    TextView loginLink,registerLink;
    UserDbHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        loginLink=(TextView) findViewById(R.id.login_link);
        registerLink=(TextView) findViewById(R.id.register_link);

        SaveBtn = (Button) findViewById(R.id.save_btn);
        userEmail = (EditText) findViewById(R.id.edt_User_email);
        newPassword = (EditText) findViewById(R.id.new_password);
        ConfirmNewPassword = (EditText) findViewById(R.id.new_confirm_password);

        databaseHelper = new UserDbHelper(ForgetPasswordActivity.this);
        SaveBtn.setOnClickListener(this);
        loginLink.setOnClickListener(this);

    }
    private void clearFields()
    {
        userEmail.setText(null);
        newPassword.setText(null);
        ConfirmNewPassword.setText(null);
    }
    private boolean inputValedation(EditText userEmail,EditText userPassword,EditText userConfirmPassword)
    {
        // Empty fields
        if(userEmail.getText().toString().isEmpty())
        {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter your email ", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userPassword.getText().toString().isEmpty())
        {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter your password ", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userConfirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter your confirmation password ", Toast.LENGTH_LONG).show();
            return false;
        }

        //
        if( !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches())
        {
            Toast.makeText(ForgetPasswordActivity.this, "Please enter correct email format", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!userPassword.getText().toString().equals(userConfirmPassword.getText().toString()))
        {
            Toast.makeText(ForgetPasswordActivity.this, "Please confirm your password correctly ", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                if(!inputValedation(userEmail, newPassword, ConfirmNewPassword))
                    break;
                boolean isExist = databaseHelper.isEmailExists(userEmail.getText().toString());
                if (isExist) {

                    databaseHelper.updatePassword(userEmail.getText().toString(),newPassword.getText().toString());
                    Toast.makeText(ForgetPasswordActivity.this, "updated.", Toast.LENGTH_LONG).show();
                    clearFields();
                    finish();

                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Go To Register", Toast.LENGTH_LONG).show();
                    clearFields();

                }
                break;
            case R.id.register_link:
                startActivity(new Intent(ForgetPasswordActivity.this, RegisterActivity.class));
                break;
            case R.id.login_link:
                finish();
                break;
        }
    }
}