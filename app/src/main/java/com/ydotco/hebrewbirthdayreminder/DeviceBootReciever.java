package com.ydotco.hebrewbirthdayreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by yotamc on 10-Oct-16.
 */

public class DeviceBootReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            AlarmMan alarmMan = new AlarmMan();
            User user = User.getInstance();
            for (Contact contact : user.contactList
                    ) {
                alarmMan.setAlarms(context, contact);
            }

            Toast.makeText(context, "\"alarm\",\"Hebrew birthday Reminder, All alarms set!\");", Toast.LENGTH_SHORT).show();
        }
    }
}
