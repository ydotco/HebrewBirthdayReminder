package com.ydotco.hebrewbirthdayreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ydotco.hebrewbirthdayreminder.database.DbContactTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by yotamc on 27-Jun-16.
 */
public class User {
    private static User user = new User();
    public ArrayList<Contact> contactList;


    public static User getInstance() {
        return user;
    }

    private User() {

        contactList = new ArrayList<>();
    }

    public void initList(Context context) {
        DbContactTable helper = new DbContactTable(context.getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("CONTACTS", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String fname = cursor.getString(cursor.getColumnIndex("FName"));
            String lName = cursor.getString(cursor.getColumnIndex("lName"));
            String eDate = cursor.getString(cursor.getColumnIndex("eDate"));
            String hDay = cursor.getString(cursor.getColumnIndex("hDay"));
            String hMonth = cursor.getString(cursor.getColumnIndex("hMonth"));
            String Year0 = cursor.getString(cursor.getColumnIndex("Year0"));
            String Year1 = cursor.getString(cursor.getColumnIndex("Year1"));
            String Year2 = cursor.getString(cursor.getColumnIndex("Year2"));
            String Year3 = cursor.getString(cursor.getColumnIndex("Year3"));
            String Year4 = cursor.getString(cursor.getColumnIndex("Year4"));
            String hYear = cursor.getString(cursor.getColumnIndex("hYear"));
            String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
            String monthReminder = cursor.getString(cursor.getColumnIndex("monthReminder"));
            String weekReminder = cursor.getString(cursor.getColumnIndex("weekReminder"));
            String dayReminder = cursor.getString(cursor.getColumnIndex("dayReminder"));

            contactList.add(new Contact(id, fname, lName, Util.CovertStringToDate(eDate),
                    new ArrayList<>(Arrays.asList(Util.CovertStringToDate(Year0),
                            Util.CovertStringToDate(Year1), Util.CovertStringToDate(Year2), Util.CovertStringToDate(Year3),
                            Util.CovertStringToDate(Year4))),
                    Util.ConvertStringToInt(hDay), (hMonth),
                    Util.ConvertStringToInt(hYear), phoneNumber, Util.ConvertStringToBool(monthReminder),
                    Util.ConvertStringToBool(weekReminder), Util.ConvertStringToBool(dayReminder)));
        }

        cursor.close();
    }



    public void AddContact(Contact newContact, Context context) {
        newContact.id = AddContactToDB(newContact, context);
        contactList.add(newContact);
    }

    public void AddContact(String fName, String lName, Date eDate,
                           int hDay, String hMonth, int hYear, String phoneNumber,
                           boolean monthReminder, boolean weekReminder, boolean dayReminder, Context context) {

        Contact newContact = new Contact();
        newContact.fName = fName;
        newContact.lName = lName;
        newContact.eDate = eDate;
        newContact.hDay = hDay;
        newContact.hMonth = hMonth;
        newContact.hYear = hYear;
        newContact.phoneNumber = phoneNumber;
        newContact.monthReminder = monthReminder;
        newContact.weekReminder = weekReminder;
        newContact.dayReminder = dayReminder;
        // newContact.GetConvertedBDays(yearx, monthx, dayx);
        newContact.id = AddContactToDB(newContact, context);
        contactList.add(newContact);
    }

    private int AddContactToDB(Contact contact, Context context) {
        long id;
        DbContactTable dbContactTable;
        SQLiteDatabase db;
        dbContactTable = new DbContactTable(context);
        db = dbContactTable.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FName", contact.fName);
        contentValues.put("lName", contact.lName);
        contentValues.put("eDate", Util.ConvertDateToString(contact.eDate));
        contentValues.put("hDay", Util.ConvertIntToString(contact.hDay));
        contentValues.put("hMonth", contact.hMonth);
        contentValues.put("Year0", Util.ConvertDateToString(contact.nextFiveYears.get(0)));
        contentValues.put("Year1", Util.ConvertDateToString(contact.nextFiveYears.get(1)));
        contentValues.put("Year2", Util.ConvertDateToString(contact.nextFiveYears.get(2)));
        contentValues.put("Year3", Util.ConvertDateToString(contact.nextFiveYears.get(3)));
        contentValues.put("Year4", Util.ConvertDateToString(contact.nextFiveYears.get(4)));
        contentValues.put("hYear", Util.ConvertIntToString(contact.hYear));
        contentValues.put("phoneNumber", contact.phoneNumber);
        contentValues.put("monthReminder", contact.monthReminder);
        contentValues.put("weekReminder", contact.weekReminder);
        contentValues.put("dayReminder", contact.dayReminder);
        id = db.insert("CONTACTS", null, contentValues);
        db.close();
        return (int) id;
    }


    private void resetContactList(Context context) {
        contactList.clear();
        initList(context);

    }

    public void deleteAll(Context context) {
        DbContactTable dbContactTable = new DbContactTable(context.getApplicationContext());
        SQLiteDatabase db = dbContactTable.getWritableDatabase();
        db.delete("CONTACTS", null, null);
        resetContactList(context);


    }


    public int EditContact(Contact contact, Context context) {

        int flag = 0;
        DbContactTable dbContactTable;
        SQLiteDatabase db;
        dbContactTable = new DbContactTable(context.getApplicationContext());
        db = dbContactTable.getWritableDatabase();
        for (Contact contactt : contactList
                ) {
            if (contactt.id == contact.id) {
                flag = 1;
                break;
            }
            if (flag == 0)
                return -1;

        }
        //update contact in db
        ContentValues contentValues = new ContentValues();
        contentValues.put("FName", contact.fName);
        contentValues.put("lName", contact.lName);
        contentValues.put("eDate", Util.ConvertDateToString(contact.eDate));
        contentValues.put("hDay", Util.ConvertIntToString(contact.hDay));
        contentValues.put("hMonth", contact.hMonth);
        contentValues.put("Year0", Util.ConvertDateToString(contact.nextFiveYears.get(0)));
        contentValues.put("Year1", Util.ConvertDateToString(contact.nextFiveYears.get(1)));
        contentValues.put("Year2", Util.ConvertDateToString(contact.nextFiveYears.get(2)));
        contentValues.put("Year3", Util.ConvertDateToString(contact.nextFiveYears.get(3)));
        contentValues.put("Year4", Util.ConvertDateToString(contact.nextFiveYears.get(4)));
        contentValues.put("hYear", Util.ConvertIntToString(contact.hYear));
        contentValues.put("phoneNumber", contact.phoneNumber);
        contentValues.put("monthReminder", contact.monthReminder);
        contentValues.put("weekReminder", contact.weekReminder);
        contentValues.put("dayReminder", contact.dayReminder);
        int i = db.update("CONTACTS", contentValues, "_id=" + contact.id, null);
        resetContactList(context);
        return i;
    }

    public int delContact(Context context,Contact contact) {
        if (contactList.isEmpty())
            return -1;
        else {
            contactList.remove(contact);
            DbContactTable dbContactTable;
            SQLiteDatabase db;
            dbContactTable = new DbContactTable(context.getApplicationContext());
            db = dbContactTable.getWritableDatabase();
            int i = db.delete("CONTACTS", "_id=" + contact.id, null);
            resetContactList(context);
            return i;
        }
    }
}
