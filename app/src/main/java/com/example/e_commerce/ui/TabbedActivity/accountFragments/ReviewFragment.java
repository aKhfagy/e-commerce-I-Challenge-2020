package com.example.e_commerce.ui.TabbedActivity.accountFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.login.Constants;
import com.example.e_commerce.login.UserDbHelper;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ReviewFragment extends Fragment  {
    private View view;
    private RecyclerView recyclerView;
    private ReviewAdapter viewAdapter;
    private ArrayList<String> reviewList;
    private UserDbHelper databaseHelper;
    private Button addBtn;
    private EditText inpReview;
    private String strSeparator = "_,_";
    private SharedPreferences sharedPreferences;

    public ReviewFragment() {

    }

    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_item_list, container, false);
        sharedPreferences = container.getContext().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        databaseHelper = new UserDbHelper(container.getContext());
        inpReview = view.findViewById(R.id.review_txt);
        addBtn = view.findViewById(R.id.add_review_btn);

        reviewList = new ArrayList<>();
        reviewList.addAll(getReviwsArrayList(databaseHelper.getUserReviews(sharedPreferences.getInt(Constants.UserTable.ID,-1))));

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        viewAdapter = new ReviewAdapter(getContext(), reviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(viewAdapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inpReview.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your review ", Toast.LENGTH_LONG).show();
                } else {
                    reviewList.add(inpReview.getText().toString());
                    databaseHelper.addReviews(convertArrayToString(reviewList),sharedPreferences.getInt(Constants.UserTable.ID,-1));
                    inpReview.setText("");
                    viewAdapter.notifyDataSetChanged();
                }

            }
        });
        return view;
    }
    public ArrayList<String> getReviwsArrayList(String str) {
        String[] arr = str.split(strSeparator);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String s : arr) {
            arrayList.add(s);
        }
        return arrayList;
    }
    public String convertArrayToString(ArrayList<String> array) {
        String reviewStr = "";
        for (int i = 0; i < array.size(); i++) {
            reviewStr = reviewStr + array.get(i);
            if (i < array.size() - 1) {
                reviewStr = reviewStr + strSeparator;
            }
        }
        return reviewStr;
    }
}

