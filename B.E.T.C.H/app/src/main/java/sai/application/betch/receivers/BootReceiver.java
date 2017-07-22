package sai.application.betch.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import sai.application.betch.FeatureFlags;
import sai.application.betch.services.PriceAlertService;
import timber.log.Timber;

/**
 * Created by sai on 7/21/17.
 */

public class BootReceiver extends BroadcastReceiver {

    private final static String alarmSPKEY = "price_alert_alarm";
    private static final String SP_NAME = "sai.application.betch.preferences";

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("Boot receiver has been called restarting the alarm");

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(context, PriceAlertService.class);
        alarmIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        if(FeatureFlags.isPeriodicAlarmEnabled()) {
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
                    (120 * 100), alarmIntent);
            SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(alarmSPKEY, true).apply();
        }
    }
}
