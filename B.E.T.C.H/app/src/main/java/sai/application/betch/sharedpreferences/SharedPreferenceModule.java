package sai.application.betch.sharedpreferences;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sai on 7/20/17.
 */

@Module
public class SharedPreferenceModule {

    @Provides
    public ISharedPreferenceService provideSharedPreferencesService(Application application) {
        return new SharedPreferenceService(application);
    }
}
