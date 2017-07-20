package sai.application.betch.alerts;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.home.CurrencyViewModel;
import sai.application.betch.network.apimodel.CryptoCurrency;
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
        return mRepository.loadAlertsFromCache().map(new Function<Alert, AlertsViewModel>() {
            @Override
            public AlertsViewModel apply(@NonNull Alert alert) throws Exception {
                AlertsViewModel alertsViewModel = new AlertsViewModel();

                alertsViewModel.setGuid(alert.getGuid());
                alertsViewModel.setCurrencyName(alert.getCurrencyName());
                alertsViewModel.setCurrencySymbol(alert.getCurrencySymbol());
                alertsViewModel.setDate(alert.getCreatedDate());
                alertsViewModel.setIsActive(alert.isActive());
                alertsViewModel.setLessThan(alert.isLessThan());
                alertsViewModel.setTimeTrigger(alert.isTimeTrigger());
                alertsViewModel.setPriceTrigger(!alert.isTimeTrigger());
                alertsViewModel.setTriggerUnit(alert.getTriggerUnit());

                return alertsViewModel;
            }
        });
    }

    @Override
    public Observable saveAlert(Alert alert) {
        return mRepository.saveAlert(alert);
    }

    @Override
    public Observable<Alert> getAlert(String guid) {
        return mRepository.getAlert(guid);
    }

    @Override
    public Observable deleteAlert(String guid) {
        return mRepository.deleteAlert(guid);
    }

    @Override
    public Observable<CurrencyViewModel> currencyData() {
        return mRepository.loadDataFromNetwork(10).concatMap(new Function<List<CryptoCurrency>, ObservableSource<CryptoCurrency>>() {
            @Override
            public ObservableSource<CryptoCurrency> apply(@NonNull List<CryptoCurrency> cryptoCurrencies) throws Exception {
                return Observable.fromIterable(cryptoCurrencies);
            }
        }).concatMap(new Function<CryptoCurrency, ObservableSource<CurrencyViewModel>>() {
            @Override
            public ObservableSource<CurrencyViewModel> apply(@NonNull CryptoCurrency cryptoCurrency) throws Exception {
                CurrencyViewModel currencyViewModel = new CurrencyViewModel();
                currencyViewModel.setFiatCurrency("USD");
                currencyViewModel.setCurrencyName(cryptoCurrency.getName());
                currencyViewModel.setCurrencySymbol(cryptoCurrency.getSymbol());
                currencyViewModel.setCostPerUnit(cryptoCurrency.getPriceUsd());
                currencyViewModel.setGoingUp(Double.valueOf(cryptoCurrency.getPercentChange1h()) >= 0);
                currencyViewModel.setId(cryptoCurrency.getId());

                return Observable.just(currencyViewModel);
            }
        });
    }

    @Override
    public Observable updateAlertIsActive(AlertsViewModel alertsViewModel) {
        return mRepository.updateAlertIsActive(alertsViewModel.getGuid(), alertsViewModel.getIsActive());
    }
}
