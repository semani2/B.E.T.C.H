package sai.application.betch.alerts;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/18/17.
 */

@Module
public class AlertsActivityModule {

    @Provides
    public AlertsActivityMVP.Presenter provideAlertsActivityPresenter(AlertsActivityMVP.Model model) {
        return new AlertsActivityPresenter(model);
    }

    @Provides
    public AlertsActivityMVP.Model provideAlertsActivityModel(IRepository repository) {
        return new AlertsActivityModel(repository);
    }
}
