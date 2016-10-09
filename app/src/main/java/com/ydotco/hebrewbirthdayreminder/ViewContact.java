package com.ydotco.hebrewbirthdayreminder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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
        //<string name="welcome_messages">Hello, %1$s! You have %2$d new messages.</string>
        //using getString(R.string.welcome_message, "Test", 0);
        //android will return a String with
        //Hello Test! you have 0 new messages

        etPhone.setText(contact.phoneNumber);

        hebBDay.setText(""+contact.hDay+" "+contact.hMonth+" "+contact.hYear);
        if(contact.monthReminder)
            MonthRe.setTextColor(ContextCompat.getColor(this,R.color.colorReminderTrue));
        else
            MonthRe.setTextColor(ContextCompat.getColor(this,R.color.colorReminderFalse));
        if(contact.weekReminder)
            WeekRe.setTextColor(ContextCompat.getColor(this,R.color.colorReminderTrue));
        else
            WeekRe.setTextColor(ContextCompat.getColor(this,R.color.colorReminderFalse));
        if(contact.dayReminder)
            DayRe.setTextColor(ContextCompat.getColor(this,R.color.colorReminderTrue));
        else
            DayRe.setTextColor(ContextCompat.getColor(this,R.color.colorReminderFalse));

        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
        nextBDay.setText(sdf.format(getNextBDay()));


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_contact_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icViewContacts:
                if(Util.getConnectivityStatus(this)==-1){
                    Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
                    return true;
                }
                Intent intent = new Intent(this, EditContact.class);
                intent.putExtra("contact",contact);
                startActivity(intent);
                return true;
            case R.id.icViewContactsDel:
               //show dialog to if sure to delete
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete "+contact.fName+"?")
                        .setIcon(R.drawable.ic_error_outline_black_24dp).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        User.getInstance().delContact(getApplication(),contact);
                        Toast.makeText(getApplication(), "Contact deleted", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}

