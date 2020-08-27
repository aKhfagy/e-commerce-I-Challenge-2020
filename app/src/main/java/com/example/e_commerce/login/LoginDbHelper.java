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

    public void addUser(User user) {
        ContentValues row = new ContentValues();
        row.put(Users.UserTable.USERNAME, user.getUsername());
        row.put(Users.UserTable.EMAIL, user.getUserEmail());
        row.put(Users.UserTable.PASSWORD, user.getPassword());
        row.put(Users.UserTable.BIRTHDATE, user.getBirthdate());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(Users.UserTable.TABLE_NAME, null, row);
        sqLiteDatabase.close();
    }


    public boolean isUserExists(String email, String password) {
            SQLiteDatabase sql = this.getReadableDatabase();
            String selectQuery = "SELECT *  FROM " + Users.UserTable.TABLE_NAME + " WHERE " + Users.UserTable.EMAIL + "= '" + email + "' AND " + Users.UserTable.PASSWORD + "= '" + password + "' ";
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
    public boolean isEmailExists(String email) {
        SQLiteDatabase sql=this.getReadableDatabase();
        String selectQuery = "SELECT *  FROM " + Users.UserTable.TABLE_NAME + " WHERE " + Users.UserTable.EMAIL +  "= '" + email +"' "  ;
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

    public void updatePassword (String email, String password) {
        ContentValues row = new ContentValues();
        row.put(Users.UserTable.PASSWORD, password);
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(Users.UserTable.TABLE_NAME, row, Users.UserTable.EMAIL + " = ?",new String[] { email });
        sqLiteDatabase.close();
    }

}