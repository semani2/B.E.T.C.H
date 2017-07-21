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
    @StringKey(OneHourJob.TAG)
    public Job provideOneHourJob(Application application) {
        return new OneHourJob(application);
    }

    @Provides
    @IntoMap
    @StringKey(TwoHourJob.TAG)
    public Job provideTwoHourJob(Application application) {
        return new TwoHourJob(application);
    }

    @Provides
    @IntoMap
    @StringKey(SixHourJob.TAG)
    public Job provideSixHourJob(Application application) {
        return new OneHourJob(application);
    }

    @Provides
    @IntoMap
    @StringKey(TwelveHourJob.TAG)
    public Job provideTwelveHourJob(Application application) {
        return new TwelveHourJob(application);
    }

    @Provides
    @IntoMap
    @StringKey(OneDayJob.TAG)
    public Job provideOneDayJob(Application application) {
        return new OneDayJob(application);
    }

    @Provides
    @Singleton
    public JobCreator provideJobCreator(Map<String, Job> map) {
        return new AppJobCreator(map);
    }

    @Provides NotificationJobMVP.Presenter provideNotificationJobPresenter(NotificationJobMVP.Model model) {
        return new NotificationJobPresenter(model);
    }

    @Provides NotificationJobMVP.Model provideNotificationJobModel(IRepository repository) {
        return new NotificationJobModel(repository);
    }
}
