package com.example.e_commerce.login;

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

import com.example.e_commerce.products.ChooseProductActivity;
import com.example.e_commerce.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn;
    EditText userEmail, userPassword;
    TextView registerLink, forgetLink;
    UserDbHelper databaseHelper;
    CheckBox checkBoxRememberMe;
    private SharedPreferences sharedPreferences;

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

        databaseHelper = new UserDbHelper(LoginActivity.this);
        sharedPreferences = this.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);

        loginBtn.setOnClickListener(this);
        registerLink.setOnClickListener(this);
        forgetLink.setOnClickListener(this);
        if (sharedPreferences.getBoolean(Constants.REMEMBER_ME, false))
            startActivity(new Intent(LoginActivity.this, ChooseProductActivity.class));
    }

    private void clearFields() {
        userEmail.setText(null);
        userPassword.setText(null);
        checkBoxRememberMe.setChecked(false);
    }

    private boolean inputValidation(EditText userEmail, EditText userPassword) {

        if (userEmail.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter your email ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (userPassword.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter your password ", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail.getText().toString()).matches()) {
            Toast.makeText(LoginActivity.this, "Please enter correct email format", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (!inputValidation(userEmail, userPassword))
                    break;
                int isExist = databaseHelper.isUserExists(userEmail.getText().toString(), userPassword.getText().toString());
                if (isExist!=-1) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Constants.UserTable.ID, isExist);
                    editor.putBoolean(Constants.REMEMBER_ME, checkBoxRememberMe.isChecked());
                    editor.apply();
                    clearFields();
                    startActivity(new Intent(LoginActivity.this, ChooseProductActivity.class));
                } else {
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