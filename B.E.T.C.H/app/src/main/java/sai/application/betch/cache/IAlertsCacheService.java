package sai.application.betch.cache;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;

/**
 * Created by sai on 7/18/17.
 */

public interface IAlertsCacheService {

    Observable<List<Alert>> getAlertsFromCache();

    Observable saveAlert(Alert alert);

    Single<Alert> getAlert(String guid);

    Observable deleteAlert(String guid);
}
