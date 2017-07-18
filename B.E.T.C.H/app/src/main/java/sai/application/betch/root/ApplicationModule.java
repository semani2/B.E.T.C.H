package sai.application.betch.root;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sai on 7/16/17.
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application provideApplication() {
        return application;
    }
}
