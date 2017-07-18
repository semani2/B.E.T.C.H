package sai.application.betch.jobscheduler;

import android.app.Application;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/18/17.
 */

@Module
public class JobsModule {

    @Provides
    @Singleton
    public JobManager provideJobManager(Application application, JobCreator jobCreator) {
        JobManager.create(application).addJobCreator(jobCreator);
        return JobManager.instance();
    }

    @Provides
    @IntoMap
    @StringKey(ShowNotificationJob.TAG)
    public Job provideShowNotificationJob(IRepository repository) {
        return new ShowNotificationJob(repository);
    }

    @Provides
    @Singleton
    public JobCreator provideJobCreator(Map<String, Job> map) {
        return new AppJobCreator(map);
    }
}
