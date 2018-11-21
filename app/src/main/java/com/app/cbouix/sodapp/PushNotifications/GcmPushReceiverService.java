package com.app.cbouix.sodapp.PushNotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.app.cbouix.sodapp.R;
import com.app.cbouix.sodapp.SplashActivity;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by CBouix on 27/04/2017.
 */

public class GcmPushReceiverService extends GcmListenerService {
    private static final String MENSAJE = "mensaje";
    private static final int NOTIF_ALERTA_ID = 0;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        //String message = data.getString("alert");
        String message = data.getString("message");
        sendNotification(message);
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSound(uri)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setContentTitle(getText(R.string.app_name))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());

    }
}