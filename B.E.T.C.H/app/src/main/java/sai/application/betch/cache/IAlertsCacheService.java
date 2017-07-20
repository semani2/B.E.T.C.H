package sai.application.betch.cache;

import io.reactivex.Observable;
import sai.application.betch.cache.cachemodel.Alert;

/**
 * Created by sai on 7/18/17.
 */

public interface IAlertsCacheService {

    Observable<Alert> getAlertsFromCache();

    Observable saveAlert(Alert alert);

    Observable<Alert> getAlert(String guid);

    Observable deleteAlert(String guid);
}
