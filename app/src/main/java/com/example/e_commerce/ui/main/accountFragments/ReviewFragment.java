package com.example.e_commerce.ui.main.accountFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.login.Constants;
import com.example.e_commerce.login.LoginDbHelper;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ReviewFragment extends Fragment  {
    View v;
    RecyclerView recyclerView;
    ArrayList<String> reviewList;
    LoginDbHelper databaseHelper;
    Button addBtn;
    EditText inpReview;
    String strSeparator = "_,_";
    private SharedPreferences sharedPreferences;

    public ReviewFragment() {

    }

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_item_list, container, false);
        inpReview = v.findViewById(R.id.review_txt);
        addBtn = v.findViewById(R.id.add_review_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Presssed ", Toast.LENGTH_LONG).show();
                if (inpReview.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your review ", Toast.LENGTH_LONG).show();
                } else {
                    reviewList.add(inpReview.getText().toString());
                    databaseHelper.addReviews(reviewList);
                }

            }
        });

        sharedPreferences = container.getContext().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        databaseHelper = new LoginDbHelper(container.getContext());


        reviewList = new ArrayList<>();
        reviewList.addAll(getReviwsArrayList(sharedPreferences.getString(Constants.UserTable.REVIEWS, "")));

        databaseHelper.convertArrayToString(reviewList);

        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        ReviewAdapter viewAdapter = new ReviewAdapter(getContext(), reviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(viewAdapter);

        return v;
    }



    public ArrayList<String> getReviwsArrayList(String str) {
        String[] arr = str.split(strSeparator);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String s : arr) {
            arrayList.add(s);
        }
        return arrayList;
    }
}

