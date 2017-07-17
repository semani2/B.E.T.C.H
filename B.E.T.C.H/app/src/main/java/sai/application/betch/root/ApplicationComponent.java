package sai.application.betch.root;

import javax.inject.Singleton;

import dagger.Component;
import sai.application.betch.home.HomeActivity;
import sai.application.betch.home.HomeActivityModule;
import sai.application.betch.network.CryptoCurrencyApiModule;

/**
 * Created by sai on 7/16/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, HomeActivityModule.class, CryptoCurrencyApiModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity target);
}
