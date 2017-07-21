package sai.application.betch.root;

import com.facebook.stetho.Stetho;
import com.orm.SugarApp;

import sai.application.betch.alerts.AlertsActivityModule;
import sai.application.betch.alerts.create_alert.CreateAlertModule;
import sai.application.betch.cache.AlertsCacheModule;
import sai.application.betch.home.HomeActivityModule;
import sai.application.betch.jobscheduler.JobsModule;
import sai.application.betch.network.CryptoCurrencyApiModule;
import sai.application.betch.services.PriceAlertServiceModule;
import sai.application.betch.sharedpreferences.SharedPreferenceModule;
import timber.log.Timber;

/**
 * Created by sai on 7/16/17.
 */

public class App extends SugarApp {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .homeActivityModule(new HomeActivityModule())
                .cryptoCurrencyApiModule(new CryptoCurrencyApiModule())
                .jobsModule(new JobsModule())
                .alertsCacheModule(new AlertsCacheModule())
                .alertsActivityModule(new AlertsActivityModule())
                .createAlertModule(new CreateAlertModule())
                .priceAlertServiceModule(new PriceAlertServiceModule())
                .sharedPreferenceModule(new SharedPreferenceModule())
                .build();

        Timber.plant(new Timber.DebugTree());

        Stetho.initializeWithDefaults(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
