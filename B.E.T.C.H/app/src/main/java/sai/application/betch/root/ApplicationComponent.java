package sai.application.betch.root;

import javax.inject.Singleton;

import dagger.Component;
import sai.application.betch.alerts.AlertsActivity;
import sai.application.betch.alerts.AlertsActivityModule;
import sai.application.betch.alerts.create_alert.CreateAlertBottomSheetDialogFragment;
import sai.application.betch.alerts.create_alert.CreateAlertModule;
import sai.application.betch.cache.AlertsCacheModule;
import sai.application.betch.home.HomeActivity;
import sai.application.betch.home.HomeActivityModule;
import sai.application.betch.jobscheduler.JobsModule;
import sai.application.betch.network.CryptoCurrencyApiModule;
import sai.application.betch.services.PriceAlertService;
import sai.application.betch.services.PriceAlertServiceModule;
import sai.application.betch.sharedpreferences.SharedPreferenceModule;

/**
 * Created by sai on 7/16/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, HomeActivityModule.class, CryptoCurrencyApiModule.class,
        JobsModule.class, AlertsCacheModule.class, AlertsActivityModule.class, CreateAlertModule.class,
        PriceAlertServiceModule.class, SharedPreferenceModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity target);

    void inject(AlertsActivity target);

    void inject(CreateAlertBottomSheetDialogFragment target);

    void inject(PriceAlertService service);
}
