package com.bvr.FriendFlow;

import android.content.ContentValues;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler extends SQLiteOpenHelper {



    private static final String DB_NAME = "userprofilesdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String BIO = "bio";
    private static final String AGE = "age";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String GENDER = "gender";
    private static final String ADDR = "addr";


    public DBHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("

                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + BIO + " TEXT,"
                + AGE + " TEXT,"
                + EMAIL + " TEXT,"
                + PHONE + " TEXT,"
                + GENDER + " TEXT,"
                + ADDR + " TEXT)";


        db.execSQL(query);

    }

    public void addNewUser(String fn, String ln, String bio, String age, String email, String phone, String gender, String addr) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIRST_NAME, fn);

        values.put(LAST_NAME, ln);
        values.put(BIO, bio);

        values.put(AGE, age);
        values.put(EMAIL, email);

        values.put(PHONE, phone);
        values.put(GENDER, gender);

        values.put(ADDR, addr);

        db.insert(TABLE_NAME, null, values);



        db.close();

    }


    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }
}