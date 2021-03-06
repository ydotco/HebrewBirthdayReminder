package com.ydotco.hebrewbirthdayreminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    User user = User.getInstance();
    Contact contact;
    EditText etFName, etLName, etPhone;
    CheckBox cbWeek, cbMonth, cbDay;
    Button btnAddContact;
    int yearx, monthx, dayx;


    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        init();
    }

    private void init() {
        yearx = 1989;
        monthx = 8;
        dayx = 10;
        contact = new Contact();
        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLname);
        etPhone = (EditText) findViewById(R.id.etPhone);
        cbDay = (CheckBox) findViewById(R.id.cbDay);
        cbMonth = (CheckBox) findViewById(R.id.cbMonth);
        cbWeek = (CheckBox) findViewById(R.id.cbWeek);
        btnAddContact = (Button) findViewById(R.id.btnAdd);
    }

    public void AddContactBtnClick(View view) {

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
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("one sec... creating contact");
        progressDialog.show();
        btnAddContact.setClickable(false);
        contact.fName = fName;
        contact.lName = lName;
        contact.phoneNumber = phone;
        contact.eDate = Util.ConvertIntToDate(yearx, monthx, dayx);
        contact.dayReminder = cbDay.isChecked();
        contact.monthReminder = cbMonth.isChecked();
        contact.weekReminder = cbWeek.isChecked();
        if (contact.GetConvertedBDays(yearx, monthx, dayx)) {
            user.AddContact(contact, this);
            Toast.makeText(AddContact.this, "Contact Added Successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddContact.this, "an Error occurred while creating contact", Toast.LENGTH_SHORT).show();
        }
        AlarmMan alarmMan = new AlarmMan();
        alarmMan.setAlarms(this,contact);
        progressDialog.dismiss();
        finish();
    }


    public void chooseDateBtnClick(View view) {
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
            yearx = year;
            monthx = monthOfYear + 1;
            dayx = dayOfMonth;
        }
    };
}



