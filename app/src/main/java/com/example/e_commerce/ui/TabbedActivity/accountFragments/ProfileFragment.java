package com.example.e_commerce.ui.TabbedActivity.accountFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.e_commerce.R;
import com.example.e_commerce.login.Constants;
import com.example.e_commerce.login.UserDbHelper;

import java.util.ArrayList;

public class ProfileFragment extends Fragment  {
    TextView userName,userEmail,userPassword, birthdate;
    private  SharedPreferences sharedPreferences;
    UserDbHelper databaseHelper;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = (TextView) view.findViewById(R.id.edt_User_name);
        userEmail = (TextView) view.findViewById(R.id.edt_User_email);
        userPassword = (TextView) view.findViewById(R.id.edt_User_Password);
        birthdate = (TextView) view.findViewById(R.id.birthday_link);

        sharedPreferences= container.getContext().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        databaseHelper = new UserDbHelper(container.getContext());
        ArrayList<String> tempUser=databaseHelper.getAllUserInfo(sharedPreferences.getInt(Constants.UserTable.ID,-1));
        if(!tempUser.isEmpty()) {
            userName.setText(tempUser.get(0));
            userEmail.setText(tempUser.get(1));
            userPassword.setText(tempUser.get(2));
            birthdate.setText(tempUser.get(3));
        }
        return view;
    }

}