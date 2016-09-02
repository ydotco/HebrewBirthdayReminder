package com.ydotco.hebrewbirthdayreminder.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by yotamc on 27-Jul-16.
 */
public class DbContactTable extends SQLiteOpenHelper {
    private static final String TABLE_NAME="CONTACTS";
    private static final String DATABASE_NAME="hebbdayremdb.db";
    private static final String UID="_id";
    private static final String FNAME="FName";
    private static final String LNAME="lName";
    private static final String EDATE="eDate";
    private static final String HDAY="hDay";
    private static final String HMONTH="hMonth";
    private static final String IN0YEARS="Year0";
    private static final String IN1YEARS="Year1";
    private static final String IN2YEARS="Year2";
    private static final String IN3YEARS="Year3";
    private static final String IN4YEARS="Year4";
    private static final String HYEAR="hYear";
    private static final String PHONENUMBER="phoneNumber";
    private static final String MONTHREMINDER="monthReminder";
    private static final String WEEKREMINDER="weekReminder";
    private static final String DAYREMINDER="dayReminder";
    private static final int  DATABASE_VERSION=1;

    public DbContactTable(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FNAME + " VARCHAR(255)," + LNAME + " VARCHAR(255)," + EDATE + " VARCHAR(255),"
                + HDAY + " VARCHAR(255)," + HMONTH + " VARCHAR(255)," + HYEAR + " VARCHAR(255),"
                + IN0YEARS + " VARCHAR(255)," + IN1YEARS + " VARCHAR(255)," + IN2YEARS + " VARCHAR(255),"
                + IN3YEARS + " VARCHAR(255)," + IN4YEARS + " VARCHAR(255)," + PHONENUMBER + " VARCHAR(255),"
                + MONTHREMINDER + "" + " VARCHAR(255)," + WEEKREMINDER + " VARCHAR(255)," + DAYREMINDER + " VARCHAR(255)); ";
        try {
            db.execSQL(sql);
        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
