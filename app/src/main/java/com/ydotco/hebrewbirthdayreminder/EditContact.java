package com.ydotco.hebrewbirthdayreminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

        contact.fName = fName;
        contact.lName = lName;
        contact.phoneNumber = phone;
        contact.dayReminder = cbDay.isChecked();
        contact.monthReminder = cbMonth.isChecked();
        contact.weekReminder = cbWeek.isChecked();
        if (dateChanged) {
            Log.d("20/09","date chane = true");
            contact.eDate = Util.ConvertIntToDate(yearx, monthx, dayx);
            if (contact.GetConvertedBDays(yearx, monthx, dayx)) {
                Log.d("20/09","get converted = true");
                user.EditContact(contact, this);
                Toast.makeText(EditContact.this, "Contact updated Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditContact.this, "an Error occurred while creating contact", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("20/09","in the else bit");

            user.EditContact(contact, this);
            Toast.makeText(EditContact.this, "Contact updated Successfully!", Toast.LENGTH_SHORT).show();
        }
        finish();
        //startActivity( new Intent(this, Main2Activity.class));

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
