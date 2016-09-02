package com.ydotco.hebrewbirthdayreminder;

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

        GetConvertedBDays();

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
    public void GetConvertedBDays() {
        //get next five years from internet\
        //add them to list
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
