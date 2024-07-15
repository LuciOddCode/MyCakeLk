package com.example.mycake.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperUser extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "mycake.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_ROLE = "user_role";

    private static final String TABLE_CATEGORY = "category";
    private static final String COLUMN_CATEGORY_ID = "category_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";

    private static final String TABLE_PRODUCT = "product";
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRODUCT_PRICE = "product_price";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "product_description";
    private static final String COLUMN_PRODUCT_IMAGE = "product_image";
    private static final String COLUMN_PRODUCT_CATEGORY = "product_category";

    private static final String TABLE_ORDER = "orders";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_USER_ID = "order_user_id";
    private static final String COLUMN_ORDER_TOTAL_PRICE = "order_total_price";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_ORDER_STATUS = "order_status";

    private static final String TABLE_ORDER_DETAIL = "order_detail";
    private static final String COLUMN_ORDER_DETAIL_ID = "order_detail_id";
    private static final String COLUMN_ORDER_DETAIL_QUANTITY = "order_detail_quantity";
    private static final String COLUMN_ORDER_DETAIL_PRICE = "order_detail_price";






    public DBHelperUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_ROLE + " TEXT)";

        String createTableCategory = "CREATE TABLE " + TABLE_CATEGORY + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT)";

        String createTableProduct = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRODUCT_PRICE + " DOUBLE, " +
                COLUMN_PRODUCT_DESCRIPTION + " TEXT, " +
                COLUMN_PRODUCT_IMAGE + " TEXT, " +
                COLUMN_PRODUCT_CATEGORY + " TEXT)";

        String createTableOrder = "CREATE TABLE " + TABLE_ORDER + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_USER_ID + " INTEGER, " +
                COLUMN_ORDER_TOTAL_PRICE + " DOUBLE, " +
                COLUMN_ORDER_DATE + " TEXT, " +
                COLUMN_ORDER_STATUS + " TEXT)";

        String createTableOrderDetail = "CREATE TABLE " + TABLE_ORDER_DETAIL + " (" +
                COLUMN_ORDER_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_ID + " INTEGER, " +
                COLUMN_PRODUCT_ID + " INTEGER, " +
                COLUMN_ORDER_DETAIL_QUANTITY + " INTEGER, " +
                COLUMN_ORDER_DETAIL_PRICE + " DOUBLE)";

        db.execSQL(createTableUser);
        db.execSQL(createTableCategory);
        db.execSQL(createTableProduct);
        db.execSQL(createTableOrder);
        db.execSQL(createTableOrderDetail);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableUser = "DROP TABLE IF EXISTS " + TABLE_USER;
        String dropTableCategory = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;
        String dropTableProduct = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
        String dropTableOrder = "DROP TABLE IF EXISTS " + TABLE_ORDER;
        String dropTableOrderDetail = "DROP TABLE IF EXISTS " + TABLE_ORDER_DETAIL;
        db.execSQL(dropTableUser);
        db.execSQL(dropTableCategory);
        db.execSQL(dropTableProduct);
        db.execSQL(dropTableOrder);
        db.execSQL(dropTableOrderDetail);
        onCreate(db);
    }

    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null);
    }

    public void addCategory(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME, name);
        db.insert(TABLE_CATEGORY, null, contentValues);
    }

    public void updateCategory(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME, name);
        db.update(TABLE_CATEGORY, contentValues, COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, COLUMN_CATEGORY_ID + " = ?", new String[]{String.valueOf(id)});
    }




}
