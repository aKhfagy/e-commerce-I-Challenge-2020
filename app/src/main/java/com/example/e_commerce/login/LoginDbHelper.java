package com.example.e_commerce.login;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;

public class LoginDbHelper extends SQLiteOpenHelper {
    String strSeparator = "_,_";
    private SQLiteDatabase sqLiteDatabase;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public LoginDbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
        sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constants.UserTable.CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Constants.UserTable.USERS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        ContentValues row = new ContentValues();
        row.put(Constants.UserTable.USERNAME, user.getUsername());
        row.put(Constants.UserTable.EMAIL, user.getUserEmail());
        row.put(Constants.UserTable.PASSWORD, user.getPassword());
        row.put(Constants.UserTable.BIRTHDATE, user.getBirthdate());
        row.put(Constants.UserTable.REVIEWS, "");
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(Constants.UserTable.USERS_TABLE_NAME, null, row);
        sqLiteDatabase.close();
    }


    public boolean isEmailExists(String email) {
        SQLiteDatabase sql = this.getReadableDatabase();
        String selectQuery = "SELECT *  FROM " + Constants.UserTable.USERS_TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + "= '" + email + "' ";
        Cursor cursor = sql.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            close();
            return true;

        } else {
            cursor.close();
            close();
            return false;
        }
    }

    public void updatePassword(String email, String password) {
        ContentValues row = new ContentValues();
        row.put(Constants.UserTable.PASSWORD, password);
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(Constants.UserTable.USERS_TABLE_NAME, row, Constants.UserTable.EMAIL + " = ?", new String[]{email});
        sqLiteDatabase.close();
    }

    public void addReviews(ArrayList<String> reviews) {
        ContentValues row = new ContentValues();
        row.put(Constants.UserTable.REVIEWS, convertArrayToString(reviews));
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(Constants.UserTable.USERS_TABLE_NAME, row, Constants.UserTable.ID + " = ?",
                new String[]{String.valueOf(this.sharedPreferences.getInt(Constants.UserTable.ID, 0))});
        sqLiteDatabase.close();
    }

    public boolean isUserExists(String email, String password, boolean rememberMe) {
        SQLiteDatabase sql = this.getReadableDatabase();
        String selectQuery = "SELECT *  FROM " + Constants.UserTable.USERS_TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + "= '" + email + "' AND " + Constants.UserTable.PASSWORD + "= '" + password + "' ";
        Cursor cursor = sql.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(Constants.UserTable.ID, cursor.getInt(cursor.getColumnIndex(Constants.UserTable.ID)));
                editor.putString(Constants.UserTable.USERNAME, cursor.getString(cursor.getColumnIndex(Constants.UserTable.USERNAME)));
                editor.putString(Constants.UserTable.EMAIL, email);
                editor.putString(Constants.UserTable.PASSWORD, password);
                editor.putString(Constants.UserTable.BIRTHDATE, cursor.getString(cursor.getColumnIndex(Constants.UserTable.BIRTHDATE)));
                editor.putString(Constants.UserTable.REVIEWS, cursor.getString(cursor.getColumnIndex(Constants.UserTable.REVIEWS)));
                editor.putBoolean(Constants.REMEMBER_ME, rememberMe);
                editor.apply();
            }
            cursor.close();
            close();
            return true;

        } else {
            cursor.close();
            close();
            return false;
        }
    }

    public String convertArrayToString(ArrayList<String> array) {
        String reviewStr = "";
        for (int i = 0; i < array.size(); i++) {
            reviewStr = reviewStr + array.get(i);
            // Do not append comma at the end of last element
            if (i < array.size() - 1) {
                reviewStr = reviewStr + strSeparator;
            }
        }
        return reviewStr;
    }


}