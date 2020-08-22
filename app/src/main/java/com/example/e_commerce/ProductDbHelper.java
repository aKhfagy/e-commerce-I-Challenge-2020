package com.example.e_commerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.Toast;

class ProductDbHelper extends SQLiteOpenHelper {

    public ProductDbHelper(Context context) {
        super(context, "products_db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor loadData() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM menu", null);
            return cursor;
        } catch (Exception ex) {
            Log.d("FAILED", ex.getMessage());
            return null;
        }
    }

}
