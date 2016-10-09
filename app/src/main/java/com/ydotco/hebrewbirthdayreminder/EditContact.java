package com.ydotco.hebrewbirthdayreminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class EditContact extends AppCompatActivity {
    private static final int DIALOG_ID = 1;
    User user = User.getInstance();
    private Contact contact;
    private EditText etFName, etLName, etPhone;
    private CheckBox cbWeek, cbMonth, cbDay;
    private int yearx, monthx, dayx;
    private boolean dateChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        contact = getIntent().getExtras().getParcelable("contact");
        initView();

        etFName.setText(contact.fName);
        etLName.setText(contact.lName);
        etPhone.setText(contact.phoneNumber);
        cbDay.setChecked(contact.dayReminder);
        cbWeek.setChecked(contact.weekReminder);
        cbMonth.setChecked(contact.monthReminder);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icEditContactDel:
                //show dialog to if sure to delete
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete " + contact.fName + "?")
                        .setIcon(R.drawable.ic_error_outline_black_24dp)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        User.getInstance().delContact(getApplication(), contact);
                        Toast.makeText(getApplication(), "Contact deleted", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void initView() {

        yearx = 1989;
        monthx = 8;
        dayx = 10;
        etFName = (EditText) findViewById(R.id.etFNameEditCon);
        etLName = (EditText) findViewById(R.id.etLNameEditCon);
        etPhone = (EditText) findViewById(R.id.etPhoneEditCon);
        cbDay = (CheckBox) findViewById(R.id.cbDayEditCon);
        cbMonth = (CheckBox) findViewById(R.id.cbMonthEditCon);
        cbWeek = (CheckBox) findViewById(R.id.cbWeekEditCon);
    }

    public void updateContactBtnClick(View view) {
        String fName = etFName.getText().toString();
        String lName = etLName.getText().toString();
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(fName)) {
            etFName.setError("please enter first name");
            return;
        }
        if (TextUtils.isEmpty(lName)) {
            etLName.setError("please enter Last name");
            return;
        }
        AlarmMan alarmMan=new AlarmMan();
        contact.fName = fName;
        contact.lName = lName;
        contact.phoneNumber = phone;
        contact.dayReminder = cbDay.isChecked();
        contact.monthReminder = cbMonth.isChecked();
        contact.weekReminder = cbWeek.isChecked();
        if (dateChanged) {
            contact.eDate = Util.ConvertIntToDate(yearx, monthx, dayx);
            if (contact.GetConvertedBDays(yearx, monthx, dayx)) {
                user.EditContact(contact, this);
                alarmMan.cancelAllAlarm(this,contact);
                alarmMan.setAlarms(this, contact);
                Toast.makeText(EditContact.this, "Contact updated Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditContact.this, "an Error occurred while creating contact", Toast.LENGTH_SHORT).show();
            }
        } else {
            alarmMan.cancelAlarm(this,contact);
            user.EditContact(contact, this);
            Toast.makeText(EditContact.this, "Contact updated Successfully!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void chooseDateBtnClickEditCon(View view) {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dPickerListener, yearx, monthx, dayx);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateChanged = true;
            yearx = year;
            monthx = monthOfYear + 1;
            dayx = dayOfMonth;
        }
    };
}
