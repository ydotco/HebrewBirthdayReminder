package com.ydotco.hebrewbirthdayreminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class ViewContact extends AppCompatActivity {
    private TextView etFName, etPhone,hebBDay,MonthRe,WeekRe,DayRe,nextBDay;
    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        contact = getIntent().getExtras().getParcelable("contact");

        initView();

        etFName.setText(contact.fName+" "+contact.lName);
        etPhone.setText(contact.phoneNumber);

        hebBDay.setText(""+contact.hDay+" "+contact.hMonth+" "+contact.hYear);
        if(contact.monthReminder)
            MonthRe.setTextColor(getResources().getColor(R.color.colorReminderTrue));
        else
            MonthRe.setTextColor(getResources().getColor(R.color.colorReminderFalse));
        if(contact.weekReminder)
            WeekRe.setTextColor(getResources().getColor(R.color.colorReminderTrue));
        else
            WeekRe.setTextColor(getResources().getColor(R.color.colorReminderFalse));
        if(contact.dayReminder)
            DayRe.setTextColor(getResources().getColor(R.color.colorReminderTrue));
        else
            DayRe.setTextColor(getResources().getColor(R.color.colorReminderFalse));
        nextBDay.setText(getNextBDay().toString());


    }

    private Date getNextBDay() {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime(),date1=null;
        for (int i = 0; (i < contact.nextFiveYears.size()); i++) {
            if (contact.nextFiveYears.get(i).compareTo(today) >=0) {
                date1 = contact.nextFiveYears.get(i);
                break;
            }
        }
        return date1;
    }


    private void initView() {
        etFName = (TextView) findViewById(R.id.tvNameViewCon);
        etPhone = (TextView) findViewById(R.id.tvPhoneViewCon);
        hebBDay= (TextView) findViewById(R.id.tvHebBDayViewCon);
        MonthRe= (TextView) findViewById(R.id.tvMonthReViewCon);
        WeekRe= (TextView) findViewById(R.id.tvWeekReViewCon);
        DayRe= (TextView) findViewById(R.id.tvDayReViewCon);
        nextBDay= (TextView) findViewById(R.id.tvNextBdayViewCon);


    }

}

