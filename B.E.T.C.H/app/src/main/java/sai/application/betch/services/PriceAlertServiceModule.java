package sai.application.betch.services;

import dagger.Module;
import dagger.Provides;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/20/17.
 */

@Module
public class PriceAlertServiceModule {

    @Provides
    public PriceAlertServiceMVP.Presenter providePriceAlertServicePresenter(PriceAlertServiceMVP.Model model) {
        return new PriceAlertServicePresenter(model);
    }

    @Provides
    public PriceAlertServiceMVP.Model providePriceAlertServiceModel(IRepository repository) {
        return new PriceAlertServiceModel(repository);
    }
}
