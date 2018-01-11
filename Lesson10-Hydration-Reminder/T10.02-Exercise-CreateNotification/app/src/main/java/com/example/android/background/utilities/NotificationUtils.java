package com.example.android.background.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.background.MainActivity;
import com.example.android.background.R;

import static android.support.v4.app.NotificationCompat.PRIORITY_HIGH;

/**
 * Utility class for creating hydration notifications
 */
public class NotificationUtils {

    private static final int PENDING_INTENT_ID = 1;
    private static final String CHANNEL_ID = "notification_channel_id";
    private static final int NOTIFY_ID = 1;

    public static void remindUserBecauseCharging(Context context) {
        // This method will create a notification for charging. It might be helpful
        // to take a look at this guide to see an example of what the code in this method will look like:
        // https://developer.android.com/training/notify-user/build-notification.html

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    context.getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeIcon(context))
                .setContentText(context.getText(R.string.charging_reminder_notification_title))
                .setContentText(context.getText(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getText(R.string.charging_reminder_notification_body)))
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)) {
            builder.setPriority(PRIORITY_HIGH);
        }
        nm.notify(NOTIFY_ID, builder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent intentMain = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, PENDING_INTENT_ID, intentMain, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
    }

}
