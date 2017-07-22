package sai.application.betch.jobscheduler;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.network.apimodel.CryptoCurrency;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/21/17.
 */

public class NotificationJobModel implements NotificationJobMVP.Model {

    private IRepository repository;

    public NotificationJobModel(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<List<Alert>> getActiveTimeAlerts(long minutes) {
        return repository.getActiveTimeAlerts(minutes).toList();
    }

    @Override
    public Observable<List<CryptoCurrency>> getCurrencyData() {
        return repository.loadData();
    }
}
