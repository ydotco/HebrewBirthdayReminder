package com.ydotco.hebrewbirthdayreminder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private CompactCalendarView compactCalendarView;
    User user = User.getInstance();
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM  yyyy", Locale.getDefault());

    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        //sort list by name
        Comparator cp = Contact.getComparator(Contact.SortParameter.DATE_ASCENDING);
        Collections.sort(user.contactList, cp);


        recyclerView = (RecyclerView) findViewById(R.id.rvCalendarContacts);
        contactAdapter = new ContactAdapter(getApplication(), getContactList());
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        if (compactCalendarView != null) {
            compactCalendarView.setShouldShowMondayAsFirstDay(false);
        }
        toolbar = getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                updateList(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
        initCalView();
        //refresh data on calendarview
        //print upcoming events barrier

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

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(Main2Activity.this, ViewContact.class);
                intent.putExtra("contact", contactAdapter.getItem(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Comparator cp = Contact.getComparator(Contact.SortParameter.DATE_ASCENDING);
        Collections.sort(user.contactList, cp);
        recyclerView.setAdapter(contactAdapter);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icAllContacts:
                Intent intent = new Intent(this, ContactList.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

   /* private void hideTitleBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
    }*/

    //setting up a list of contacts to be shown at the listview
    private List<Contact> getContactList() {
        return user.contactList;
    }


    //add dates to calendar
    private void initCalView() {
        for (Contact contact : user.contactList
                ) {
            for (Date date : contact.nextFiveYears
                    ) {
                compactCalendarView.addEvent(new Event(Color.BLUE, date.getTime(), contact), true);
            }
        }
    }

    //when a date is clicked show relevant contacts
    private void updateList(Date date) {
        List<Event> dayList = compactCalendarView.getEvents(date);
        final List<Contact> filteredList = new ArrayList<>();
        for (int i = 0; i < dayList.size(); i++) {
            filteredList.add((Contact) dayList.get(i).getData());
        }
        ContactAdapter contactAdapter2 = new ContactAdapter(this, filteredList);
        recyclerView.setAdapter(contactAdapter2);
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
                if (Util.getConnectivityStatus(this) == -1) {
                    Toast.makeText(this, "You must connect to the internet in order to add a contact", Toast.LENGTH_LONG).show();
                    break;
                }
                Intent intent = new Intent(this, AddContact.class);
                startActivity(intent);
                break;
            case R.id.fab2:
                Toast.makeText(Main2Activity.this, "coming soon...", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    //returns to current day on calendar view and contact list
    public void BackToToday(View view) {

        compactCalendarView.setCurrentDate(Calendar.getInstance().getTime());
        recyclerView.setAdapter(contactAdapter);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                              final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }

                    super.onLongPress(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}







