package sai.application.betch.jobscheduler;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import sai.application.betch.R;
import sai.application.betch.alerts.create_alert.Constants;
import sai.application.betch.home.HomeActivity;
import sai.application.betch.root.App;
import timber.log.Timber;

/**
 * Created by sai on 7/18/17.
 */

public class ShowNotificationService extends android.app.Service implements NotificationJobMVP.Service {
    @Inject
    NotificationJobMVP.Presenter presenter;

    private long minutes;

    final CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void onCreate() {
        super.onCreate();
        ((App)getApplication()).getComponent().inject(this);
        presenter.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getExtras() == null) {
            Timber.d("Servie has null extrs, cannot do much! ");
            return START_NOT_STICKY;
        }

        Bundle bundle = intent.getExtras();
        minutes = bundle.getLong(Constants.MINUTES_KEY);
        Timber.d("running backgroun job for " + minutes + " mins");

        presenter.getNotificationMessage(minutes);

        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {

        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected String getNotificationTitle(long minutes) {
        long hours = minutes / 60;
        if (hours == 1) {
            return getResources().getString(R.string.str_time_notification_title_onehour);
        } else {
            return getResources().getString(R.string.str_time_notification_title, hours);
        }
    }

    protected void showNotification(String title, String msg) {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, HomeActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.ic_access_alarm)
                .setShowWhen(true)
                .setLocalOnly(true)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .build();

        NotificationManagerCompat.from(this)
                .notify(new Random().nextInt(), notification);

        presenter.rxUnsubscribe();
        countDownLatch.countDown();
    }

    @Override
    public void showNotification(String msg) {
        showNotification(getNotificationTitle(minutes), msg);
    }
}
