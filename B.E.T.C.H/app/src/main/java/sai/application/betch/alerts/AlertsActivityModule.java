package sai.application.betch.alerts;

import com.evernote.android.job.JobManager;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/18/17.
 */

@Module
public class AlertsActivityModule {

    @Provides
    public AlertsActivityMVP.Presenter provideAlertsActivityPresenter(AlertsActivityMVP.Model model, JobManager jobManager) {
        return new AlertsActivityPresenter(model, jobManager);
    }

    @Provides
    public AlertsActivityMVP.Model provideAlertsActivityModel(IRepository repository) {
        return new AlertsActivityModel(repository);
    }
}
