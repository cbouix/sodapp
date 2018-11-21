package com.app.cbouix.sodapp.PushNotifications;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.cbouix.sodapp.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by CBouix on 27/04/2017.
 */

public class GcmIntentServices extends IntentService {

    private static final String TAG = "RegIntentService";
    public static final String REGISTRATION_SUCCES = "Registration succes";
    public static final String REGISTRATION_FAIL = "Registration fail";
    public static final String TOKEN = "Token";

    public GcmIntentServices() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent registration = null;
        String token = null;
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String senderId = getString(R.string.gcm_defaultSenderId);
            token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            registration = new Intent(REGISTRATION_SUCCES);
            registration.putExtra(TOKEN, token);
        }catch (Exception e){
            registration = new Intent(REGISTRATION_FAIL);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(registration);
    }
}
