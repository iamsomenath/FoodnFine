package snd.orgn.foodnfine.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;


import androidx.core.app.NotificationCompat;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.DasboardActivity;


public class MyNotificationManager {  // For Oreo Version

    private Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void showNotificationMessage(final String title, final String message) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Notification Channel
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(Config.CHANNEL_ID, Config.CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mCtx, Config.CHANNEL_ID)
                        .setSmallIcon(R.drawable.new_logo)
                        .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.new_logo))
                        .setContentTitle(title)
                        .setContentText(message);

        builder.setSmallIcon(R.drawable.new_logo);

        Intent intent = new Intent(mCtx, DasboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.new_logo));
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(mCtx.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, builder.build());
    }

}