package com.example.mycake.db;

import android.content.Context;
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
                COLUMN_PRODUCT_PRICE + " TEXT, " +
                COLUMN_PRODUCT_DESCRIPTION + " TEXT, " +
                COLUMN_PRODUCT_IMAGE + " TEXT, " +
                COLUMN_PRODUCT_CATEGORY + " TEXT)";

        db.execSQL(createTableUser);
        db.execSQL(createTableCategory);
        db.execSQL(createTableProduct);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableUser = "DROP TABLE IF EXISTS " + TABLE_USER;
        String dropTableCategory = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;
        String dropTableProduct = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
        db.execSQL(dropTableUser);
        db.execSQL(dropTableCategory);
        db.execSQL(dropTableProduct);
        onCreate(db);
    }
}
