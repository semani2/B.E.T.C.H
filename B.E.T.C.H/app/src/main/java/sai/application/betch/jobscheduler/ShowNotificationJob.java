package sai.application.betch.jobscheduler;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import sai.application.betch.R;
import sai.application.betch.home.HomeActivity;
import sai.application.betch.repository.IRepository;
import timber.log.Timber;

/**
 * Created by sai on 7/18/17.
 */

public class ShowNotificationJob extends Job {
    static final String TAG = "show_notification_tag";

    private IRepository repository;

    public ShowNotificationJob(IRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Timber.d("Running background job");
        PendingIntent pi = PendingIntent.getActivity(getContext(), 0,
                new Intent(getContext(), HomeActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle("B.E.T.C.H")
                .setContentText("Hurry up!! BitCoin just went up by $100.")
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setColor(Color.RED)
                .setLocalOnly(true)
                .build();

        NotificationManagerCompat.from(getContext())
                .notify(new Random().nextInt(), notification);

        return Result.SUCCESS;
    }

    public static JobRequest buildJobRequest() {
        Timber.d("Requesting for show notification job");
        return new JobRequest.Builder(ShowNotificationJob.TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build();
    }
}
