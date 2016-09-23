package com.ydotco.hebrewbirthdayreminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerview;
    private List<Contact> mContactModel;
    private ContactAdapter adapter;
    User user = User.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        recyclerview = (RecyclerView) findViewById(R.id.rvContactList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerview, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(ContactList.this, ViewContact.class);
                    intent.putExtra("contact",adapter.getItem(position));
              /*  for (int i = 0; i <mContactModel.size() ; i++) {
                    if (2>3);

                }*/


                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mContactModel = new ArrayList<>();
        mContactModel.addAll(user.contactList);

        //sort list by name
        Comparator cp = Contact.getComparator(Contact.SortParameter.NAME_ASCENDING);
        Collections.sort(mContactModel, cp);


        adapter = new ContactAdapter(this, mContactModel);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.contact_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        query = query.toLowerCase();
        final List<Contact> filteredModelList = new ArrayList<>();

        for (Contact model : mContactModel) {
            final String textF = model.fName.toLowerCase();
            final String textL = model.lName.toLowerCase();

            if (textF.contains(query) || textL.contains(query)) {
                filteredModelList.add(model);
            }
        }
        adapter.animateTo(filteredModelList);
        recyclerview.scrollToPosition(0);
        return true;
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ContactList.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView,
                              final ContactList.ClickListener clickListener) {
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
