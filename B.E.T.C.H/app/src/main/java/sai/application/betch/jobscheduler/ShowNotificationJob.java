package sai.application.betch.jobscheduler;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.evernote.android.job.Job;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import sai.application.betch.R;
import sai.application.betch.home.HomeActivity;
import timber.log.Timber;

/**
 * Created by sai on 7/18/17.
 */

public abstract class ShowNotificationJob extends Job implements NotificationJobMVP.Job {
    @Inject
    protected NotificationJobMVP.Presenter presenter;

    protected Application application;

    public ShowNotificationJob(Application application) {
        this.application = application;
    }

    final CountDownLatch countDownLatch = new CountDownLatch(1);

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Timber.d("Running background job for " + getNotificationFrequencyInMinutes() + " mins");
        presenter.getNotificationMessage(getNotificationFrequencyInMinutes());

        try {
            countDownLatch.await();
        }
        catch (InterruptedException e) {

        }
        return Result.SUCCESS;
    }

    protected String getNotificationTitle(long minutes) {
        long hours = minutes / 60;
        if (hours == 1) {
            return getContext().getResources().getString(R.string.str_time_notification_title_onehour);
        } else {
            return getContext().getResources().getString(R.string.str_time_notification_title, hours);
        }
    }

    protected abstract long getNotificationFrequencyInMinutes();

    protected void showNotification(String title, String msg) {
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), HomeActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle("B.E.T.C.H")
                .setContentText(title)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setLocalOnly(true)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .build();

        NotificationManagerCompat.from(getContext())
                .notify(new Random().nextInt(), notification);

        presenter.rxUnsubscribe();
        countDownLatch.countDown();
    }

}
