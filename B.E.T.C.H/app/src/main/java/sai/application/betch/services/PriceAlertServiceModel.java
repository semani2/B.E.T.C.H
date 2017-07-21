package sai.application.betch.services;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.network.apimodel.CryptoCurrency;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/20/17.
 */

public class PriceAlertServiceModel implements PriceAlertServiceMVP.Model {
    private IRepository mRepository;

    public PriceAlertServiceModel(IRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public Single<List<Alert>> getActivePriceAlerts() {
        return mRepository.getActivePriceAlerts().toList();
    }

    @Override
    public Observable<List<CryptoCurrency>> getCurrencyData() {
        return mRepository.loadDataFromNetwork(20);
    }
}
