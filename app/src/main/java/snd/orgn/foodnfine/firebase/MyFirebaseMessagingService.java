package snd.orgn.foodnfine.firebase;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import snd.orgn.foodnfine.application.FoodnFine;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static MyFirebaseMessagingService mInstance = null;
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    int count = 0;
    private LocalBroadcastManager broadcaster;

    public MyFirebaseMessagingService() {
    }

    public static MyFirebaseMessagingService getInstance() {
        if (mInstance == null) {
            mInstance = new MyFirebaseMessagingService();
        }
        return mInstance;
    }


    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            //handleNotification(remoteMessage.getNotification().getBody());
            //Toast.makeText(mInstance, remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //Log.d(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                //Log.d(TAG, "onMessageReceived: " + json.toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.d(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        //NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());

        //Log.e(TAG, "push json: " + json.toString());

        try {
            //  JSONObject jsonObject = new JSONObject(tfun);
            JSONObject data = json.getJSONObject("order");
            //Log.d(TAG, "handleDataMessage: " + data);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", data.getString("message"));
                pushNotification.putExtra("title", data.getString("title"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    MyNotificationManager.getInstance(getApplicationContext())
                            .showNotificationMessage(data.getString("title"), data.getString("message"));
                } else {
                    NotificationHandler notificationHandler = new NotificationHandler(getApplicationContext());
                    notificationHandler.showNotificationMessage(data.getString("title"), data.getString("message"));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN", s);
        FoodnFine.getAppSharedPreference().saveDevKey(s);
    }
}
