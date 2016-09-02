package com.ydotco.hebrewbirthdayreminder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        recyclerView = (RecyclerView) findViewById(R.id.rvCalendarContacts);

        contactAdapter=new ContactAdapter(getApplication(),getContactList());
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

        initcalView();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                updateList(year, month, dayOfMonth);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //setting up a list of contacts to be shown at the listview
    private List<Contact> getContactList() {
        List<Contact> contacts = new ArrayList<>();
        String[] fNames = {"Yoni", "Ora", "Vered", "Ofek", "Asher"};
        String[] lNames = {"sade", "Loepold", "Engel", "Benzer", "Schwartz"};
        int[] dayDates = {22, 10, 1, 17, 8};


        for (int i = 0; i < fNames.length; i++) {
            Contact contactt = new Contact();
            contactt.fName = fNames[i];
            contactt.lName = lNames[i];
            contactt.hMonth = Util.hebMonths[i];
            contactt.hDay = dayDates[i];
            contacts.add(contactt);
        }
        return contacts;
    }

    private void initcalView() {
        //add dates to calendar
    }

    private void updateList(int year, int month, int dayOfMonth) {
    }

}
