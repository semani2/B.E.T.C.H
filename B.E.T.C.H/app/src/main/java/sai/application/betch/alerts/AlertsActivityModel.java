package sai.application.betch.alerts;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsActivityModel implements AlertsActivityMVP.Model {

    private IRepository mRepository;

    public AlertsActivityModel(IRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public Observable<AlertsViewModel> data() {
        return null;
    }

    @Override
    public Observable saveAlert(Alert alert) {
        return mRepository.saveAlert(alert);
    }

    @Override
    public Single<Alert> getAlert(String guid) {
        return mRepository.getAlert(guid);
    }

    @Override
    public Observable deleteAlert(String guid) {
        return mRepository.deleteAlert(guid);
    }
}
