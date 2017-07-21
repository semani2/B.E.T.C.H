package sai.application.betch.alerts.create_alert;

import com.evernote.android.job.JobManager;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.alerts.AlertsActivityMVP;

/**
 * Created by sai on 7/19/17.
 */

@Module
public class CreateAlertModule {

    @Provides
    public CreateAlertMVP.Presenter provideCreateAlertPresenter(AlertsActivityMVP.Model model, JobManager manager) {
        return new CreateAlertPresenter(model, manager);
    }
}
