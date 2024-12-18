package com.example.stok2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STOCK = "stock";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_STOCK + " INTEGER)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }


    public void updateProduct(int id, String name, String description, int stock) {
        SQLiteDatabase db = this.getWritableDatabase(); // Veritabanı bağlantısını açıyoruz

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);  // Ürün adı
        values.put(COLUMN_DESCRIPTION, description);  // Ürün açıklaması
        values.put(COLUMN_STOCK, stock);  // Ürün stoğu (stock_amount değil, COLUMN_STOCK olmalı)

        // Veritabanındaki ürünü güncelliyoruz
        int rowsUpdated = db.update(TABLE_PRODUCTS, values, "id = ?", new String[]{String.valueOf(id)});

        if (rowsUpdated > 0) {
            Log.d("Database", "Ürün başarıyla güncellendi.");
        } else {
            Log.d("Database", "Ürün güncellenemedi.");
        }

        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(String name, String description, int stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_STOCK, stock);

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                int stock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK));

                productList.add(new Product(id, name, description, stock));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
