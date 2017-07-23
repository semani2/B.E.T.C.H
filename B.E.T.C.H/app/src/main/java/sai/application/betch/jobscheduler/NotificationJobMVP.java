package sai.application.betch.jobscheduler;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/21/17.
 */

public interface NotificationJobMVP {

    interface Service {
        void showNotification(String msg);
    }

    interface Presenter{

        void setService(Service service);

        void rxUnsubscribe();

        void getNotificationMessage(long minutes);
    }

    interface Model{
        Single<List<Alert>> getActiveTimeAlerts(long minutes);

        Observable<List<CryptoCurrency>> getCurrencyData();
    }
}
