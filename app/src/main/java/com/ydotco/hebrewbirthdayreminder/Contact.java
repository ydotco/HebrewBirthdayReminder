package com.ydotco.hebrewbirthdayreminder;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ydotco.hebrewbirthdayreminder.convert.from.network.Jsonizer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yotamc on 27-Jul-16.
 */
public class Contact implements Parcelable {

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

    private Contact(Parcel in) {
        id = in.readInt();
        image = in.readInt();
        fName = in.readString();
        lName = in.readString();
        eDate = new Date(in.readLong());
        nextFiveYears=new ArrayList<Date>();
        nextFiveYears.add(new Date(in.readLong()));
        nextFiveYears.add(new Date(in.readLong()));
        nextFiveYears.add(new Date(in.readLong()));
        nextFiveYears.add(new Date(in.readLong()));
        nextFiveYears.add(new Date(in.readLong()));
        hDay = in.readInt();
        hMonth = in.readString();
        hYear = in.readInt();
        phoneNumber = in.readString();
        monthReminder = in.readInt() == 0;
        weekReminder = in.readInt() == 0;
        dayReminder = in.readInt() == 0;
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
        Jsonizer jsonizer = new Jsonizer();
        String sDay = Util.ConvertIntToString(dayx);
        String sMonth = Util.ConvertIntToString(monthx);
        String sYear = Util.ConvertIntToString(yearx);
        jsonizer.makeHebEngRequest("http://www.hebcal.com/converter/?cfg=json&gy=" + sYear + "&gm=" + sMonth + "&gd=" + sDay + "&g2h=1");
        int tried = 0;
        while (jsonizer.done == false) {
            if (tried < 5) {
                try {
                    Thread.sleep(1000);
                    tried++;
                    Log.i("info", "slept for 1 sec");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }

        }
        hDay = jsonizer.hebEngDate.getHd();
        hMonth = jsonizer.hebEngDate.getHm();
        hYear = jsonizer.hebEngDate.getHy();
        for (Date date : jsonizer.list) {
            nextFiveYears.add(date);
        }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //when reading, order matters!

        dest.writeInt(id);
        dest.writeInt(image);
        dest.writeString(fName);
        dest.writeString(lName);
        dest.writeLong(eDate.getTime()); //to read use new Date(input.readLong())
        dest.writeLong((nextFiveYears.get(0)).getTime());
        dest.writeLong((nextFiveYears.get(1)).getTime());
        dest.writeLong((nextFiveYears.get(2)).getTime());
        dest.writeLong((nextFiveYears.get(3)).getTime());
        dest.writeLong((nextFiveYears.get(4)).getTime());
        dest.writeInt(hDay);
        dest.writeString(hMonth);
        dest.writeInt(hYear);
        dest.writeString(phoneNumber);
        dest.writeInt(monthReminder ? 0 : 1); //read like this:  myboolean = parcel.readInt() == 0;
        dest.writeInt(weekReminder ? 0 : 1);
        dest.writeInt(dayReminder ? 0 : 1);
    }

    public static final Parcelable.Creator<Contact> CREATOR
            = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };


}
