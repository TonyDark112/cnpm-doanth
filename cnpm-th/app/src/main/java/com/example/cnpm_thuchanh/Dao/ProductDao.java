package com.example.cnpm_thuchanh.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cnpm_thuchanh.DatabaseHelper.DatabaseHelper;
import com.example.cnpm_thuchanh.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private SQLiteDatabase db;

    public ProductDao(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM Product", null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            int cateId = c.getInt(1);
            String name = c.getString(2);
            String desc = c.getString(3);
            double price = c.getDouble(4);
            String img = c.getString(5);
            list.add(new Product(id, cateId, name, desc, price, img));
        }
        c.close();
        return list;
    }

    public void insert(Product p) {
        ContentValues values = new ContentValues();
        values.put("cateid", p.getCateId());
        values.put("name", p.getName());
        values.put("description", p.getDescription());
        values.put("price", p.getPrice());
        values.put("imagepath", p.getImagePath());
        db.insert("Product", null, values);
    }

    public void update(Product p) {
        ContentValues values = new ContentValues();
        values.put("cateid", p.getCateId());
        values.put("name", p.getName());
        values.put("description", p.getDescription());
        values.put("price", p.getPrice());
        values.put("imagepath", p.getImagePath());
        db.update("Product", values, "id=?", new String[]{String.valueOf(p.getId())});
    }

    public void delete(int id) {
        db.delete("Product", "id=?", new String[]{String.valueOf(id)});
    }
}

