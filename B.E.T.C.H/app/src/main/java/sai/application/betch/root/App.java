package sai.application.betch.root;

import android.app.Application;

import sai.application.betch.home.HomeActivityModule;
import sai.application.betch.network.CryptoCurrencyApiModule;
import timber.log.Timber;

/**
 * Created by sai on 7/16/17.
 */

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .homeActivityModule(new HomeActivityModule())
                .cryptoCurrencyApiModule(new CryptoCurrencyApiModule())
                .build();

        Timber.plant(new Timber.DebugTree());
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
