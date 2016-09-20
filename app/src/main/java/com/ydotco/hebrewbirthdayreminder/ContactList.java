package com.ydotco.hebrewbirthdayreminder;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
                Log.d("query","isside if"+textF.contains(query)+" and "+ textL.contains(query));
                filteredModelList.add(model);
            }
        }
        //fix when deleting query
        Log.d("query",query);
            adapter.animateTo(filteredModelList);
        recyclerview.scrollToPosition(0);
        return true;
    }
}
