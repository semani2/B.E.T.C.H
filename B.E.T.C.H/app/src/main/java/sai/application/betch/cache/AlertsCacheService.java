package sai.application.betch.cache;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import sai.application.betch.cache.cachemodel.Alert;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsCacheService implements IAlertsCacheService {
    @Override
    public Observable<Alert> getAlertsFromCache() {
        List<Alert> result = Alert.listAll(Alert.class);
        Collections.reverse(result);
        return Observable.fromIterable(result);
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

    @Override
    public Observable<Alert> getActivePriceAlerts() {
        List<Alert> result = Alert.find(Alert.class, "is_active = ? and is_time_trigger = ?", "1", "0");
        return Observable.fromIterable(result);
    }

    @Override
    public Observable<Alert> getActiveTimeAlerts(long minutes) {
        List<Alert> result = Alert.find(Alert.class, "is_active = ? and is_time_trigger = ? and trigger_unit = ?",
                "1", "1", String.valueOf(minutes));
        return Observable.fromIterable(result);
    }
}
