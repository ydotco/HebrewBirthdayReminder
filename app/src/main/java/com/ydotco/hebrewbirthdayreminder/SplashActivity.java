package com.ydotco.hebrewbirthdayreminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT =3000 ;
    User user=User.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        user.initList(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, Main2Activity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void addToDB(View view) {
        Date date = null;
        Date date0 = null;
        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        Date date4 = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("24/07/1989");
             date0 = new SimpleDateFormat("dd/MM/yyyy").parse("24/07/2016");
             date1 = new SimpleDateFormat("dd/MM/yyyy").parse("24/07/2017");
             date2 = new SimpleDateFormat("dd/MM/yyyy").parse("24/07/2018");
             date3 = new SimpleDateFormat("dd/MM/yyyy").parse("24/07/2019");
             date4 = new SimpleDateFormat("dd/MM/yyyy").parse("24/07/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Contact contact=new Contact();
        contact.id=14;
        contact.fName="veled";
        contact.lName="sade";
        contact.eDate=date;
        contact.nextFiveYears.add(date0);
        contact.nextFiveYears.add(date1);
        contact.nextFiveYears.add(date2);
        contact.nextFiveYears.add(date3);
        contact.nextFiveYears.add(date4);
        contact.hDay=22;
        contact.hMonth=Util.hebMonths[1];
        contact.hYear=5729;
        contact.phoneNumber="0547277291";
        contact.monthReminder=true;      //
        contact.weekReminder=true;       // when the contact should be notified
        contact.dayReminder=true;


        Toast.makeText(SplashActivity.this, "the returned value is "+ user.EditContact(contact,this), Toast.LENGTH_SHORT).show();
    }

}
