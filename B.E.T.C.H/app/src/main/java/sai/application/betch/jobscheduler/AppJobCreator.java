package sai.application.betch.jobscheduler;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by sai on 7/18/17.
 */

public class AppJobCreator implements JobCreator {

    @Inject
    Map<String, Provider<Job>> jobs;

    @Inject
    AppJobCreator() {}

    @Override
    public Job create(String tag) {
        Provider<Job> jobProvider = jobs.get( tag );
        return jobProvider != null ? jobProvider.get() : null;
    }
}
