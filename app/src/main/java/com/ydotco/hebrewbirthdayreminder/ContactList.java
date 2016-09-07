package com.ydotco.hebrewbirthdayreminder;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerview;
    private List<Contact> mContactModel;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        recyclerview = (RecyclerView) findViewById(R.id.rvContactList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        //setHasOptionsMenu(true);
        //String[] locales = Locale.getISOCountries();
        mContactModel = new ArrayList<>();

       // for (String countryCode : locales) {
        //    Locale obj = new Locale("", countryCode);
       //     mCountryModel.add(new CountryModel(obj.getDisplayCountry(), obj.getISO3Country()));
       // }

       // adapter = new RVAdapter(mCountryModel);
      ////  recyclerview.setAdapter(adapter);
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
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement the filter logic
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
