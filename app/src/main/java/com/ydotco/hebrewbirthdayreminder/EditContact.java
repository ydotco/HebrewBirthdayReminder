package com.ydotco.hebrewbirthdayreminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EditContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Contact contact=getIntent().getExtras().getParcelable("contact");
        System.out.println("++++after-> "+contact.toString());
    }
}
