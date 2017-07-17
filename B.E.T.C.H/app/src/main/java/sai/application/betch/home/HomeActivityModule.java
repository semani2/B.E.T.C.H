package sai.application.betch.home;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sai on 7/16/17.
 */

@Module
public class HomeActivityModule {

    @Provides
    public HomeActivityMVP.Presenter provideHomeActivityPresenter(HomeActivityMVP.Model model) {
        return new HomeActivityPresenter(model);
    }

    @Provides
    public HomeActivityMVP.Model provideHomeActivityModel(IRepository repository) {
        return new HomeActivityModel(repository);
    }

    @Provides IRepository provideRepository() {
        return new Repository();
    }
}
