package sai.application.betch.analytics;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sai on 7/23/17.
 */

@Module
public class AnalyticsModule {

    @Singleton
    @Provides
    public FirebaseHelper provideFirebaseHelper(Application application) {
        return new FirebaseHelper(application);
    }
}
