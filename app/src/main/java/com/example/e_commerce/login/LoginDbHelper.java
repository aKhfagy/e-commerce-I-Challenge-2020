package com.example.e_commerce.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public LoginDbHelper(Context context) {
        super(context, Users.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Users.UserTable.createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Users.UserTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addUser(String username,String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setUserEmail(email);
        user.setPassword(password);
        ContentValues row = new ContentValues();
        row.put(Users.UserTable.USERNAME, user.getUsername());
        row.put(Users.UserTable.EMAIL, user.getUserEmail());
        row.put(Users.UserTable.PASSWORD, user.getPassword());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(Users.UserTable.TABLE_NAME, null, row);
        sqLiteDatabase.close();
    }


    public boolean isUserExists(String email, String password) {
        SQLiteDatabase sql=this.getReadableDatabase();
        //String query=" select "+Users.UserTable.ID+" from "+Users.UserTable.TABLE_NAME+" LIMIT 1 WHERE "+ Users.UserTable.USERNAME+" = "+username;
        String selectQuery = "SELECT *  FROM " + Users.UserTable.TABLE_NAME + " WHERE " + Users.UserTable.EMAIL +  "= '" + email + "' AND " + Users.UserTable.PASSWORD + "= '"+ password +"' "  ; ;
         Cursor cursor =sql.rawQuery(selectQuery,null);
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


}