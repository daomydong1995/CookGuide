package com.example.daomy.foodguide.ultil;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by PDNghiaDev on 4/14/2015.
 * Create Database for application
 */
public class Database extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "/data/data/com.example.daomy.foodguide/databases/";
    // Database Name
    private static final String DATABASE_NAME = "FoodGuide.db";


    public Context mContext;
    private SQLiteDatabase db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public void openDatabase() throws SQLException{
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Method copy database from assets folder to internal
    public void CopyDataBaseFromAsset() throws IOException{
        File a = new File(DATABASE_PATH + DATABASE_NAME);
        if (a.exists()){
            return;
        }

        // Open your local database as the input stream
        InputStream in = mContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty database
        String outputFileName = DATABASE_PATH + DATABASE_NAME;
        File databaseFile = new File(DATABASE_PATH);
        // Check if database folder exists, if not create one and its subfolders
        if (!databaseFile.exists()){
            databaseFile.mkdir();
        }

        OutputStream out = new FileOutputStream(outputFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }

        out.flush();
        out.close();
        in.close();
    }

}
