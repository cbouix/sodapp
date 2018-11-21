package com.app.cbouix.sodapp.PushNotifications;

import android.content.Intent;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by CBouix on 27/04/2017.
 */

public class GcmTokenRefreshListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GcmListenerService.class);
        startService(intent);
    }
}