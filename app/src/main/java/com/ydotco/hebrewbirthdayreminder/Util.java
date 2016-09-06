package com.ydotco.hebrewbirthdayreminder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yotamc on 14-Aug-16.
 */
public class Util {
    static final String[] hebMonths = {"Nisan", "Iyyar", "Sivan", "Tamuz", "Av", "Elul", "Tishrei", "Cheshvan", "Kislev", "Tevet", "Shvat", "Adar1", "Adar2"};

    static public int ConvertStringToInt(String stringInt) {
        return Integer.parseInt(stringInt);
    }

    static public String ConvertIntToString(int intString) {
        return String.valueOf(intString);
    }

    static public String ConvertDateToString(Date dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = null;
        try {
            date = simpleDateFormat.format(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    static public Date CovertStringToDate(String stringDate) {

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date ConvertIntToDate(int yearx, int monthx, int dayx) {
        String mgd;
        String mgm = ConvertIntToString(monthx);
        String mgy = ConvertIntToString(yearx);
        if (dayx < 10)
            mgd = "0" + Util.ConvertIntToString(dayx);
        else
            mgd = Util.ConvertIntToString(dayx);
        String s = mgd + "/" + mgm + "/" + mgy;
        return Util.CovertStringToDate(s);
    }
}

