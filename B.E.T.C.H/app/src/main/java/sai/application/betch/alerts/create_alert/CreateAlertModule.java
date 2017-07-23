package sai.application.betch.alerts.create_alert;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.alerts.AlertsActivityMVP;
import sai.application.betch.analytics.FirebaseHelper;

/**
 * Created by sai on 7/19/17.
 */

@Module
public class CreateAlertModule {

    @Provides
    public CreateAlertMVP.Presenter provideCreateAlertPresenter(AlertsActivityMVP.Model model, FirebaseHelper firebaseHelper) {
        return new CreateAlertPresenter(model);
    }
}
