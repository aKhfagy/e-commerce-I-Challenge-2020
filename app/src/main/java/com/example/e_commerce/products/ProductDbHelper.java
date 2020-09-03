package com.example.e_commerce.products;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class ProductDbHelper extends SQLiteOpenHelper {
    public static String DB_PATH= "data/data/com.example.e_commerce/databases/";
    public static String DB_NAME = "menu";
    private final Context context;
    private SQLiteDatabase dbObj;
    public String[] tableNames = new String[]{
            "product_db_Food",
            "product_db_Nuggets",
            "product_db_Salads",
            "product_db_HappyMeal",
            "product_db_McCafeDrinks",
            "product_db_SweetTreats",
            "product_db_BreakfastMeals",
            "product_db_MorningPlatters"
    };

    public ProductDbHelper(Context context) {
        super(context, DB_NAME, null, 6);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createDB() throws IOException {

        this.getReadableDatabase();
        Log.i("Readable ends........","end");

        try {
            copyDB();
            Log.i("copy db ends.......","end");

        } catch (IOException e) {

            throw new Error("Error copying database");
        }
    }

    private boolean checkDB(){

        SQLiteDatabase checkDB = null;

        try{
            String path = DB_PATH + DB_NAME;
            Log.i("myPath ......",path);
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

            Log.i("myPath ......",path);
            if (checkDB!=null)
            {
                Cursor c= checkDB.rawQuery("SELECT * FROM product_db_Food", null);
                Log.i("Cursor.......",c.getString(0));
                c.moveToFirst();
                String contents[]=new String[80];
                int flag=0;

                while(! c.isAfterLast())
                {
                    String temp="";
                    String s2=c.getString(0);
                    String s3=c.getString(1);
                    String s4=c.getString(2);
                    temp=temp+"\n Id:"+s2+"\tType:"+s3+"\tBal:"+s4;
                    contents[flag]=temp;
                    flag=flag+1;

                    Log.i("DB values.........",temp);
                    c.moveToNext();

                }
            }
            else
            {
                return false;
            }

        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null){

            checkDB.close();

        }
        return checkDB != null;
    }

    public void copyDB() throws IOException{
        try {
            Log.i("inside copyDB.........","start");

            InputStream ip =  context.getAssets().open(DB_NAME+".db");
            Log.i("Input Stream....",ip+"");
            String op=  DB_PATH  +  DB_NAME ;
            OutputStream output = new FileOutputStream( op);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = ip.read(buffer))>0){
                output.write(buffer, 0, length);
                Log.i("Content.... ",length+"");
            }
            output.flush();
            output.close();
            ip.close();
        }
        catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void openDB() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.i("open DB......",dbObj.toString());
    }

    @Override
    public synchronized void close() {

        if(dbObj != null)
            dbObj.close();

        super.close();
    }

    public void start() {
        try {

            createDB();
        } catch (IOException ioe) {

            throw new Error("Database not created....");
        }

        try {
            openDB();

        }catch(SQLException sqle){

            throw sqle;
        }
    }

    public Cursor[] loadData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor[] cursors = new Cursor[tableNames.length];

        for(int i = 0; i < tableNames.length; ++i) {
            try {
                String query = "SELECT * FROM " + tableNames[i];
                cursors[i] = db.rawQuery(query, null);
            } catch (Exception ex) {
                Log.d("FAILED", ex.getMessage());
                return null;
            }
        }

        return cursors;
    }
}
