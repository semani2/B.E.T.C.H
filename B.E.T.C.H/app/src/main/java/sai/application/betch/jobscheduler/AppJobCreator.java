package sai.application.betch.jobscheduler;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by sai on 7/18/17.
 */

public class AppJobCreator implements JobCreator {

    @Inject
    Map<String, Job> jobs;

    @Inject
    AppJobCreator(Map<String, Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public Job create(String tag) {
       Job job = jobs.get(tag);
        return job;
    }
}
