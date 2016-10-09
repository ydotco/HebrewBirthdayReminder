package com.ydotco.hebrewbirthdayreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by yotamc on 09-Oct-16.
 */

class AlarmMan {

    //goes over the contacts birthdays and sets alarms for 5 years
    void setAlarms(Context context, Contact contact) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Date date : contact.nextFiveYears) {
            if (contact.monthReminder) {
                Intent notificationIntentMonth = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntentMonth.putExtra("contact", contact);
                notificationIntentMonth.putExtra("reminder", Util.MONTH_REMINDER);
                notificationIntentMonth.addCategory("android.intent.category.DEFAULT");
                int alarmId = Util.getAlarmId(contact.id, true, false, false);
                PendingIntent broadcastMonth = PendingIntent.getBroadcast
                        (context, alarmId, notificationIntentMonth, PendingIntent.FLAG_UPDATE_CURRENT);
                Date monthDate = getMonthBefore(date);
                alarmManager.set(AlarmManager.RTC_WAKEUP, monthDate.getTime()
                        , broadcastMonth);
                Log.i("Alarm", "Month alarm set on date " + monthDate.toString() + " with id " + alarmId);
            }
            if (contact.weekReminder) {
                Intent notificationIntentWeek = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntentWeek.putExtra("contact", contact);
                notificationIntentWeek.putExtra("reminder", Util.WEEK_REMINDER);
                notificationIntentWeek.addCategory("android.intent.category.DEFAULT");
                int alarmId = Util.getAlarmId(contact.id, false, true, false);
                PendingIntent broadcastWeek = PendingIntent.getBroadcast
                        (context, alarmId, notificationIntentWeek, PendingIntent.FLAG_UPDATE_CURRENT);
                Date weekDate = getWeekBefore(date);
                alarmManager.set(AlarmManager.RTC_WAKEUP, weekDate.getTime(), broadcastWeek);
                Log.i("Alarm", "week alarm set on date " + weekDate.toString() + " with id " + alarmId);
            }
            if (contact.dayReminder) {
                Intent notificationIntentDay = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                notificationIntentDay.putExtra("contact", contact);
                notificationIntentDay.putExtra("reminder", Util.DAY_REMINDER);
                notificationIntentDay.addCategory("android.intent.category.DEFAULT");
                int alarmId = Util.getAlarmId(contact.id, false, false, true);
                PendingIntent broadcastDay = PendingIntent.getBroadcast
                        (context, alarmId, notificationIntentDay, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), broadcastDay);
                Log.i("Alarm", "Day alarm set on date " + date.toString() + " with id " + alarmId);
            }
        }
    }

    //cancels unwanted alarms
    void cancelAlarm(Context context, Contact contact) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (!contact.monthReminder) {
            Intent notificationIntentMonth = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntentMonth.putExtra("contact", contact);
            notificationIntentMonth.putExtra("reminder", Util.MONTH_REMINDER);
            notificationIntentMonth.addCategory("android.intent.category.DEFAULT");
            int alarmId = Util.getAlarmId(contact.id, true, false, false);
            PendingIntent broadcastMonth = PendingIntent.getBroadcast
                    (context, alarmId, notificationIntentMonth, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(broadcastMonth);

            Log.i("Alarm", "alarm canceled, id " + alarmId);
        }
        if (!contact.weekReminder) {
            Intent notificationIntentWeek = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntentWeek.putExtra("contact", contact);
            notificationIntentWeek.putExtra("reminder", Util.WEEK_REMINDER);
            notificationIntentWeek.addCategory("android.intent.category.DEFAULT");
            int alarmId = Util.getAlarmId(contact.id, false, true, false);
            PendingIntent broadcastWeek = PendingIntent.getBroadcast
                    (context, alarmId, notificationIntentWeek, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(broadcastWeek);
            Log.i("Alarm", "week alarm canceled, id " + alarmId);
        }
        if (!contact.dayReminder) {
            Intent notificationIntentDay = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntentDay.putExtra("contact", contact);
            notificationIntentDay.putExtra("reminder", Util.DAY_REMINDER);
            notificationIntentDay.addCategory("android.intent.category.DEFAULT");
            int alarmId = Util.getAlarmId(contact.id, false, false, true);
            PendingIntent broadcastDay = PendingIntent.getBroadcast
                    (context, alarmId, notificationIntentDay, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(broadcastDay);
            Log.i("Alarm", "Day alarm canceled with id " + alarmId);
        }
    }

    //cancel all contact alarms
    void cancelAllAlarm(Context context, Contact contact) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntentMonth = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntentMonth.putExtra("contact", contact);
        notificationIntentMonth.putExtra("reminder", Util.MONTH_REMINDER);
        notificationIntentMonth.addCategory("android.intent.category.DEFAULT");
        int alarmIdM = Util.getAlarmId(contact.id, true, false, false);
        PendingIntent broadcastMonth = PendingIntent.getBroadcast
                (context, alarmIdM, notificationIntentMonth, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(broadcastMonth);
        Log.i("Alarm", "alarm canceled, id " + alarmIdM);
        Intent notificationIntentWeek = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntentWeek.putExtra("contact", contact);
        notificationIntentWeek.putExtra("reminder", Util.WEEK_REMINDER);
        notificationIntentWeek.addCategory("android.intent.category.DEFAULT");
        int alarmIdW = Util.getAlarmId(contact.id, false, true, false);
        PendingIntent broadcastWeek = PendingIntent.getBroadcast
                (context, alarmIdW, notificationIntentWeek, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(broadcastWeek);
        Log.i("Alarm", "week alarm canceled, id " + alarmIdW);

        Intent notificationIntentDay = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntentDay.putExtra("contact", contact);
        notificationIntentDay.putExtra("reminder", Util.DAY_REMINDER);
        notificationIntentDay.addCategory("android.intent.category.DEFAULT");
        int alarmIdD = Util.getAlarmId(contact.id, false, false, true);
        PendingIntent broadcastDay = PendingIntent.getBroadcast
                (context, alarmIdD, notificationIntentDay, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(broadcastDay);
        Log.i("Alarm", "Day alarm canceled with id " + alarmIdD);

    }

    private Date getMonthBefore(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.HOUR, 9);
        return cal.getTime();
    }

    private Date getWeekBefore(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        cal.set(Calendar.HOUR, 9);
        return cal.getTime();
    }


}
