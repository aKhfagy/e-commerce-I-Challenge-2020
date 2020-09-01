package com.example.e_commerce.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UserDbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public UserDbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);

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
        row.put(Constants.UserTable.REVIEWS,"Your reviwes:");
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

    public void addReviews(String reviews,int userId) {
        ContentValues row = new ContentValues();
        row.put(Constants.UserTable.REVIEWS, reviews);
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(Constants.UserTable.USERS_TABLE_NAME, row, Constants.UserTable.ID + " = ?",
                new String[]{String.valueOf(userId)});
        sqLiteDatabase.close();
    }

    public int isUserExists(String email, String password) {
        sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT *  FROM " + Constants.UserTable.USERS_TABLE_NAME + " WHERE " + Constants.UserTable.EMAIL + "= '" + email + "' AND " + Constants.UserTable.PASSWORD + "= '" + password + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(Constants.UserTable.ID));
        }
        sqLiteDatabase.close();
        return -1;
    }
    public ArrayList<String> getAllUserInfo(int userTd) {
        ArrayList<String> tempUser=new ArrayList<String>();
        sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT *  FROM " + Constants.UserTable.USERS_TABLE_NAME + " WHERE " + Constants.UserTable.ID + "= '" + userTd + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {

            tempUser.add(cursor.getString(cursor.getColumnIndex(Constants.UserTable.USERNAME)));
            tempUser.add(cursor.getString(cursor.getColumnIndex(Constants.UserTable.EMAIL)));
            tempUser.add(cursor.getString(cursor.getColumnIndex(Constants.UserTable.PASSWORD)));
            tempUser.add(cursor.getString(cursor.getColumnIndex(Constants.UserTable.BIRTHDATE)));
        }
        sqLiteDatabase.close();
        return tempUser;
    }
    public String getUserEmail(int userTd) {
        sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT *  FROM " + Constants.UserTable.USERS_TABLE_NAME + " WHERE " + Constants.UserTable.ID + "= '" + userTd + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(Constants.UserTable.EMAIL));
        }
        sqLiteDatabase.close();
        return "";
    }
    public String getUserReviews(int userTd) {
        sqLiteDatabase = this.getReadableDatabase();
        String selectQuery = "SELECT *  FROM " + Constants.UserTable.USERS_TABLE_NAME + " WHERE " + Constants.UserTable.ID + "= '" + userTd + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(Constants.UserTable.REVIEWS));
        }
        sqLiteDatabase.close();
        return "";
    }
}