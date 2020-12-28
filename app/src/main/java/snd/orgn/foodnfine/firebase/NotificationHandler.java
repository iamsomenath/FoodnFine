package snd.orgn.foodnfine.firebase;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.activity.DasboardActivity;

public class NotificationHandler {

    private Context mContext;

    public NotificationHandler(Context mContext) {
        this.mContext = mContext;
    }

    //This method would display the notification
    public void showNotificationMessage(final String title, final String message) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mContext, Config.CHANNEL_ID)
                        .setSmallIcon(R.drawable.new_logo)
                        .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.new_logo))
                        .setContentTitle(title)
                        .setContentText(message);

        Intent intent = new Intent(mContext, DasboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        //builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_round));
        builder.setSmallIcon(R.drawable.new_logo);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, builder.build());
    }
}