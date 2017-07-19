package sai.application.betch.cache;

import java.util.List;

import io.reactivex.Observable;
import sai.application.betch.cache.cachemodel.Alert;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsCacheService implements IAlertsCacheService {
    @Override
    public Observable<Alert> getAlertsFromCache() {
        return Observable.fromIterable(Alert.listAll(Alert.class));
    }

    @Override
    public Observable saveAlert(Alert alert) {
        alert.save();
        return Observable.empty();
    }

    @Override
    public Observable<Alert> getAlert(String guid) {
        return Observable.fromIterable(Alert.find(Alert.class, "guid = ?", guid));
    }

    @Override
    public Observable deleteAlert(String guid) {
        List<Alert> alerts = Alert.find(Alert.class, "guid = ?", guid);
        if(alerts != null && alerts.size() > 0) {
            alerts.get(0).delete();
        }
        return Observable.empty();
    }
}
