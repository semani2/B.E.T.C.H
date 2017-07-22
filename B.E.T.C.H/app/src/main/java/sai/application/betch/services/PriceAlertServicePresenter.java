package sai.application.betch.services;

import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.network.apimodel.CryptoCurrency;
import timber.log.Timber;

/**
 * Created by sai on 7/20/17.
 */

public class PriceAlertServicePresenter implements PriceAlertServiceMVP.Presenter {
    private PriceAlertServiceMVP.Model model;
    private PriceAlertServiceMVP.Service service;

    public PriceAlertServicePresenter(PriceAlertServiceMVP.Model model) {
        this.model = model;
    }

    public void setService(PriceAlertServiceMVP.Service service) {
        this.service = service;
    }

    @Override
    public void getNotificationMessage() {
        Observable<List<CryptoCurrency>> currencyDataObservable = model.getCurrencyData();
        Observable<List<Alert>> alertObservable = model.getActivePriceAlerts().toObservable();

        Observable<String> observable = Observable.zip(currencyDataObservable, alertObservable, new BiFunction<List<CryptoCurrency>, List<Alert>, String>() {
            @Override
            public String apply(@NonNull List<CryptoCurrency> cryptoCurrencies, @NonNull List<Alert> alerts) throws Exception {
                String notificationMsg;
                StringBuilder stringBuilder = new StringBuilder();
                boolean shouldShowNotification = false;

                if(alerts.size() == 0) {
                    return null;
                }
                Iterator<Alert> alertIterator = alerts.iterator();
                while ((alertIterator.hasNext()))
                {
                    Alert currentAlert = alertIterator.next();
                    for(CryptoCurrency currency : cryptoCurrencies) {
                        if(currentAlert.getCurrencyId().equalsIgnoreCase(currency.getId())) {
                            // Check price
                            if(currentAlert.isLessThan()) {
                                if(Double.parseDouble(currency.getPriceUsd()) <= currentAlert.getTriggerUnit()) {
                                    shouldShowNotification = true;
                                    stringBuilder.append(getNotificationStringForCurrency(currency, currentAlert));
                                    model.updateAlertIsActive(currentAlert.getGuid(), false).subscribe();
                                }
                            }
                            else {
                                if(Double.parseDouble(currency.getPriceUsd()) > currentAlert.getTriggerUnit()) {
                                    shouldShowNotification = true;
                                    stringBuilder.append(getNotificationStringForCurrency(currency, currentAlert));
                                    model.updateAlertIsActive(currentAlert.getGuid(), false).subscribe();
                                }
                            }
                        }
                    }
                }
                notificationMsg = shouldShowNotification ? stringBuilder.toString() : null;
                return notificationMsg;
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.d("Price alerts service subscribed");
                    }

                    @Override
                    public void onNext(String s) {
                        Timber.d("here is the string notification! " + s);
                        service.showNotification(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error in Price Service " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getNotificationStringForCurrency(CryptoCurrency currency, Alert alert) {
        if(alert.isLessThan()) {
            return "1 "+ currency.getSymbol() + " < USD " + alert.getTriggerUnit() +"\n";
        }
        else {
            return "1 "+ currency.getSymbol() + " > USD " + alert.getTriggerUnit() +"\n";
        }
    }
}
