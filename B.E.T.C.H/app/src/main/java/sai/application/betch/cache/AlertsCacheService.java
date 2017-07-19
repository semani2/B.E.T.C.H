package sai.application.betch.cache;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsCacheService implements IAlertsCacheService {
    @Override
    public Observable<List<Alert>> getAlertsFromCache() {
        return null;
    }

    @Override
    public Observable saveAlert(Alert alert) {
        return null;
    }

    @Override
    public Single<Alert> getAlert(String guid) {
        return null;
    }

    @Override
    public Observable deleteAlert(String guid) {
        return null;
    }
}
