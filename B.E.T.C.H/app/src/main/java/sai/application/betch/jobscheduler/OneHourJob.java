package sai.application.betch.jobscheduler;

import android.support.annotation.NonNull;

import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/21/17.
 */

public class OneHourJob extends ShowNotificationJob {
    public OneHourJob(IRepository repository) {
        super(repository);
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        return null;
    }
}
