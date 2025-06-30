package com.example.cnpm_thuchanh.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shop.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Category
        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY," +
                "name NVARCHAR(20) NOT NULL" +
                ");");

        // Tạo bảng Product
        db.execSQL("CREATE TABLE Product (" +
                "id INTEGER PRIMARY KEY," +
                "cateid INTEGER NOT NULL," +
                "name NVARCHAR(40) NOT NULL," +
                "description NVARCHAR(40) NOT NULL," +
                "price DECIMAL(10,2) NOT NULL," +
                "imagepath VARCHAR(255)," +
                "FOREIGN KEY (cateid) REFERENCES Category(id)" +
                ");");

        // Tạo bảng Cart
        db.execSQL("CREATE TABLE Cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ");");

        // Tạo bảng CartItem
        db.execSQL("CREATE TABLE CartItem (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cart_id INTEGER," +
                "product_id INTEGER," +
                "quantity INTEGER," +
                "FOREIGN KEY (cart_id) REFERENCES Cart(id)," +
                "FOREIGN KEY (product_id) REFERENCES Product(id)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa nếu đã tồn tại
        db.execSQL("DROP TABLE IF EXISTS CartItem");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Category");
        onCreate(db);
    }
}

