package sai.application.betch.root;

import javax.inject.Singleton;

import dagger.Component;
import sai.application.betch.HomeActivity;

/**
 * Created by sai on 7/16/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity target);
}
