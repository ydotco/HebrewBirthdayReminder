package com.ydotco.hebrewbirthdayreminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    User user=User.getInstance();
    CalendarView calendarView;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        //hideTitleBar();

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        recyclerView = (RecyclerView) findViewById(R.id.rvCalendarContacts);

        contactAdapter = new ContactAdapter(getApplication(), getContactList());
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

        initcalView();
        //refresh data on calendarview
        //print upcoming events

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                updateList(year, month, dayOfMonth);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icAllContacts:
                Intent intent =new Intent(this,ContactList.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void hideTitleBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
    }

    //setting up a list of contacts to be shown at the listview
    private List<Contact> getContactList() {
       /* List<Contact> contacts = new ArrayList<>();
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
        return contacts;*/
        return user.contactList;
    }

    private void initcalView() {

        //add dates to calendar
    }
    //when a date is clicked on show relevant contacts
    private void updateList(int year, int month, int dayOfMonth) {
    }

    public void animateFAB() {

        if (isFabOpen) {
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                Intent intent = new Intent(this, AddContact.class);
                startActivity(intent);
                break;
            case R.id.fab2:
                Intent intent1=new Intent(this,EditContact.class);
                Contact contactq=user.contactList.get(0);
                System.out.println("++++before-> "+contactq.toString());
                intent1.putExtra("contact",contactq);
                startActivity(intent1);
                Toast.makeText(Main2Activity.this, "coming soon...", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}

