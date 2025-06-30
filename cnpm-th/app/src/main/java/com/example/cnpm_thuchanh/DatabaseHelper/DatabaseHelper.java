package com.example.cnpm_thuchanh.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shop.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +  // ✅ Tự tăng
                "name TEXT NOT NULL)");

        // Bạn có thể tạo thêm bảng Product, Cart, CartItem ở đây nếu cần
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS Category");
        onCreate(db);
    }
}


