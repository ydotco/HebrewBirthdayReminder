package com.ydotco.hebrewbirthdayreminder;

import android.util.Log;

import com.ydotco.hebrewbirthdayreminder.convert.from.network.Jsonizer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yotamc on 27-Jul-16.
 */
public class Contact {

    public int id;
    public int image;
    public String fName;
    public String lName;
    public Date eDate;
    public ArrayList<Date> nextFiveYears;
    public int hDay;
    public String hMonth;
    public int hYear;
    public String phoneNumber;
    boolean monthReminder;      //
    boolean weekReminder;       // when the contact should be notified
    boolean dayReminder;        //

    //ctors
    public Contact() {
        nextFiveYears = new ArrayList<>();

    }

    public Contact(String fName, String lName, Date eDate, int hDay, String hMonth, int hYear,
                   String phoneNumber, boolean monthReminder, boolean weekReminder,
                   boolean dayReminder) {
        this.fName = fName;
        this.lName = lName;
        this.eDate = eDate;
        this.hDay = hDay;
        this.hMonth = hMonth;
        this.hYear = hYear;
        this.phoneNumber = phoneNumber;
        this.monthReminder = monthReminder;
        this.weekReminder = weekReminder;
        this.dayReminder = dayReminder;


    }

    public Contact(int id, String fName, String lName, Date eDate, ArrayList<Date> nextFiveYears,
                   int hDay, String hMonth, int hYear, String phoneNumber, boolean monthReminder,
                   boolean weekReminder, boolean dayReminder) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.eDate = eDate;
        this.nextFiveYears = nextFiveYears;
        this.hDay = hDay;
        this.hMonth = hMonth;
        this.hYear = hYear;
        this.phoneNumber = phoneNumber;
        this.monthReminder = monthReminder;
        this.weekReminder = weekReminder;
        this.dayReminder = dayReminder;
    }

    //methods
    public Boolean GetConvertedBDays(int yearx, int monthx, int dayx) {
        Jsonizer jsonizer=new Jsonizer();
        String sDay=Util.ConvertIntToString(dayx);
        String sMonth=Util.ConvertIntToString(monthx);
        String sYear=Util.ConvertIntToString(yearx);
        jsonizer.makeHebEngRequest("http://www.hebcal.com/converter/?cfg=json&gy="+sYear+"&gm="+sMonth+"&gd="+sDay+"&g2h=1");
        int tried=0;
        while (jsonizer.done==false)
        {
            if (tried<5)
            {try {
                Thread.sleep(1000);
                tried++;
                Log.i("info","slept for 1 sec");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }}
            else {return false;}

        }
        hDay=jsonizer.hebEngDate.getHd();
        hMonth=jsonizer.hebEngDate.getHm();
        hYear=jsonizer.hebEngDate.getHy();
        for (Date date:jsonizer.list) {nextFiveYears.add(date);}
        return true;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", eDate=" + eDate +
                ", nextFiveYears=" + nextFiveYears +
                ", hDay=" + hDay +
                ", hMonth=" + hMonth +
                ", hYear=" + hYear +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", monthReminder=" + monthReminder +
                ", weekReminder=" + weekReminder +
                ", dayReminder=" + dayReminder +
                '}';
    }
}
