package com.ydotco.hebrewbirthdayreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import java.util.Random;

/**
 * Created by yotamc on 21-Sep-16.
 */

public class AlarmReceiver extends BroadcastReceiver {
    User user = User.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {

        user.initList(context);
        Random random=new Random();
        int randomId=random.nextInt(9999);
        Contact contact = intent.getExtras().getParcelable("contact");


        Intent notificationIntent = new Intent(context, ViewContact.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context)
                .addParentStack(ViewContact.class);
        notificationIntent.putExtra("contact", contact);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent((int)System.currentTimeMillis(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        String contentText;
        switch (intent.getIntExtra("reminder",-1)){
            case Util.MONTH_REMINDER:
                contentText ="It will be " + (contact != null ? contact.fName : null) + "'s hebrew birthday in one Month!";
                break;
            case Util.WEEK_REMINDER:
                contentText ="It will be " + (contact != null ? contact.fName : null) + "'s hebrew birthday in a week!";
                break;
            case Util.DAY_REMINDER:
                contentText ="it's " + (contact != null ? contact.fName : null) + " hebrew birthday today!";
                break;
            default:
                contentText ="it's " + (contact != null ? contact.fName : null) + " hebrew birthday today! by default";
                break;
        }
        Notification notification = builder.setContentTitle("Mazal Tov!")
                .setContentText(contentText)
                .setTicker("Mazal Tov!")
                .setSmallIcon(R.drawable.birthday_image)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 200, 300})
                .build();


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(randomId, notification);
    }
}
