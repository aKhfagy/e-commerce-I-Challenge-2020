package com.example.e_commerce.ui.main.accountFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.e_commerce.R;
import com.example.e_commerce.login.Constants;

public class ProfileFragment extends Fragment  {
    TextView userName,userEmail,userPassword, birthdate;
    private  SharedPreferences sharedPreferences;
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
        userName.setText(sharedPreferences.getString(Constants.UserTable.USERNAME,""));
        userEmail.setText(sharedPreferences.getString(Constants.UserTable.EMAIL,""));
        userPassword.setText(sharedPreferences.getString(Constants.UserTable.PASSWORD,""));
        birthdate.setText(sharedPreferences.getString(Constants.UserTable.BIRTHDATE,""));
        Toast.makeText(getActivity(), "Presssed ", Toast.LENGTH_LONG).show();
        return view;
    }

}