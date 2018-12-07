package com.app.cbouix.sodapp.Application;

import android.app.Application;

import com.app.cbouix.sodapp.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
/**
 * Created by CBouix on 19/08/2017.
 */

public class AnalyticsApplication extends Application {
    private Tracker mTracker;
    private static GoogleAnalytics sAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = GoogleAnalytics.getInstance(this);
    }
    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            //mTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}