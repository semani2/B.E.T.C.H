package sai.application.betch.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import sai.application.betch.cache.cachemodel.Alert;

/**
 * Created by sai on 7/23/17.
 */

@Singleton
public class FirebaseHelper {
    private FirebaseAnalytics mFirebaseAnalytics;

    public FirebaseHelper(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void logAlertCreatedEvent(Alert alert) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, alert.getCurrencyName());
        bundle.putDouble(FirebaseAnalytics.Param.ITEM_CATEGORY, alert.getTriggerUnit());

        mFirebaseAnalytics.logEvent("ALERT_CREATED", bundle);
    }
}
