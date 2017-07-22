package sai.application.betch.services;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/20/17.
 */

public interface PriceAlertServiceMVP {
    interface Service{
        void showNotification(String msg);
    }

    interface Presenter{
        void setService(Service service);

        void getNotificationMessage();
    }

    interface Model{
        Single<List<Alert>> getActivePriceAlerts();

        Observable<List<CryptoCurrency>> getCurrencyData();

        Observable updateAlertIsActive(String guid, boolean isActive);
    }
}
