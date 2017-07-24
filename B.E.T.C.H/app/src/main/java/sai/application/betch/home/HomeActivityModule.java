package sai.application.betch.home;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.analytics.FirebaseHelper;
import sai.application.betch.cache.IAlertsCacheService;
import sai.application.betch.cache.ICurrencyCacheService;
import sai.application.betch.network.CryptoCurrencyApiService;
import sai.application.betch.repository.IRepository;
import sai.application.betch.repository.Repository;
import sai.application.betch.sharedpreferences.ISharedPreferenceService;

/**
 * Created by sai on 7/16/17.
 */

@Module
public class HomeActivityModule {

    @Provides
    public HomeActivityMVP.Presenter provideHomeActivityPresenter(HomeActivityMVP.Model model, FirebaseHelper firebaseHelper) {
        return new HomeActivityPresenter(model, firebaseHelper);
    }

    @Provides
    public HomeActivityMVP.Model provideHomeActivityModel(IRepository repository) {
        return new HomeActivityModel(repository);
    }

    @Singleton
    @Provides
    IRepository provideRepository(CryptoCurrencyApiService apiService, IAlertsCacheService cacheService,
                                  ISharedPreferenceService service, ICurrencyCacheService currencyCacheService) {
        return new Repository(apiService, cacheService, service, currencyCacheService);
    }
}
