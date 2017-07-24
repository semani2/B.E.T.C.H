package sai.application.betch.alerts;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.analytics.FirebaseHelper;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/18/17.
 */

@Module
public class AlertsActivityModule {

    @Provides
    public AlertsActivityMVP.Presenter provideAlertsActivityPresenter(AlertsActivityMVP.Model model, FirebaseHelper firebaseHelper) {
        return new AlertsActivityPresenter(model, firebaseHelper);
    }

    @Provides
    public AlertsActivityMVP.Model provideAlertsActivityModel(IRepository repository) {
        return new AlertsActivityModel(repository);
    }
}
