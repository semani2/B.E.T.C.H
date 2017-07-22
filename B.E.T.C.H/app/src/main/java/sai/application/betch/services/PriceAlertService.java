package sai.application.betch.services;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import sai.application.betch.R;
import sai.application.betch.home.HomeActivity;
import sai.application.betch.root.App;
import timber.log.Timber;

/**
 * Created by sai on 7/20/17.
 */

public class PriceAlertService extends Service implements PriceAlertServiceMVP.Service{
    @Inject
    PriceAlertServiceMVP.Presenter mPresenter;

    @Inject
    Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        ((App)getApplication()).getComponent().inject(this);
        Timber.d("Price alert service started!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Timber.d("Price service onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPresenter.setService(this);

        Timber.d("Price alert service starting now...");

        // Checking if app is in foreground
        ActivityManager activityManager = (ActivityManager)application.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName()
                .equalsIgnoreCase(application.getPackageName())) {
            isActivityFound = true;
        }


        if(!isActivityFound) {
            mPresenter.getNotificationMessage();
            return START_STICKY;
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    public void showNotification(String msg) {
        PendingIntent pi = PendingIntent.getActivity(application, 0,
                new Intent(application, HomeActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(application)
                .setContentTitle("Here's the market update \n")
                .setContentText(msg)
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setLocalOnly(true)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .build();

        NotificationManagerCompat.from(application)
                .notify(new Random().nextInt(), notification);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        Timber.d("Price alert service destroyed");
    }
}
