package edu.hanu.mycart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "carts.db";
    public static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public DbHelper(@Nullable Context context, @Nullable String DB_NAME, @Nullable SQLiteDatabase.CursorFactory  factory, @Nullable int DB_VERSION) {
        super(context, DB_NAME, factory, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Created");
        //create tables
        String sql = "CREATE TABLE carts (" +
                "cart_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "product_id INTEGER NOT NULL, " +
                "product_name TEXT NOT NULL," +
                "product_category TEXT NOT NULL," +
                "product_price INTEGER NOT NULL," +
                "product_thumbnail TEXT NOT NULL," +
                "quantity INTEGER NOT NULL" +
                ")";
        db.execSQL(sql);
        //seed demo data
//        sql = "INSERT INTO carts (cart_id, product_id, quantity) VALUES('Normal Shirt', 'Shirt', 1)";
//        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //production: alter table
        String sql = "ALTER TABLE carts " +
                "ADD newtable";
        db.execSQL(sql);
        //development: drop tables & recreate
        sql = "DROP TABLE IF EXISTS carts";
        db.execSQL(sql);
    }

}
