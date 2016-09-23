package com.ydotco.hebrewbirthdayreminder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yotamc on 14-Aug-16.
 */
class Util {
    static final String[] hebMonths = {"Nisan", "Iyyar", "Sivan", "Tamuz", "Av", "Elul", "Tishrei", "Cheshvan", "Kislev", "Tevet", "Shvat", "Adar1", "Adar2"};

    static int ConvertStringToInt(String stringInt) {
        return Integer.parseInt(stringInt);
    }

    static  String ConvertIntToString(int intString) {
        return String.valueOf(intString);
    }

    static  String ConvertDateToString(Date dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = null;
        try {
            date = simpleDateFormat.format(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    static  Boolean ConvertStringToBool(String stringBool) {
        return (stringBool.equals("1"));
    }
//return -1 if no connection
    static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_WIMAX) {
                Log.d("networkStatus", "Wifi");
                return ConnectivityManager.TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.d("networkStatus", "MOBILE");
                return ConnectivityManager.TYPE_MOBILE;
            }
        }
        Log.d("networkStatus", "Not Connected");
        return -1;
    }
    static  Date CovertStringToDate(String stringDate) {

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

     static Date ConvertIntToDate(int yearx, int monthx, int dayx) {
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

