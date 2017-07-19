package sai.application.betch.cache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sai on 7/18/17.
 */

@Module
public class AlertsCacheModule {

    @Provides
    @Singleton
    public IAlertsCacheService provideAlertsCacheService() {
        return new AlertsCacheService();
    }
}
