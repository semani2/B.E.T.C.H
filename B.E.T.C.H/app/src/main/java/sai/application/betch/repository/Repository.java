package sai.application.betch.repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import sai.application.betch.cache.IAlertsCacheService;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.network.CryptoCurrencyApiService;
import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/16/17.
 */

public class Repository implements IRepository {

    private CryptoCurrencyApiService mCryptoCurrencyApiService;
    private IAlertsCacheService mAlertsCacheService;

    private List<CryptoCurrency> mCryptoCurrencyData;
    private long mTimeStamp;

    private static final long STALE_MS = 15 * 1000;

    public Repository(CryptoCurrencyApiService apiService, IAlertsCacheService alertsCacheService) {
        this.mCryptoCurrencyApiService = apiService;
        this.mAlertsCacheService = alertsCacheService;
        mTimeStamp = System.currentTimeMillis();
        mCryptoCurrencyData = new ArrayList<>();
    }

    private boolean isDataUptoDate() {
        return System.currentTimeMillis() - mTimeStamp < STALE_MS;
    }

    @Override
    public Observable<List<CryptoCurrency>> loadDataFromNetwork(int limit) {
        return mCryptoCurrencyApiService.getCurrencyData(limit)
                .doOnNext(new Consumer<List<CryptoCurrency>>() {
                    @Override
                    public void accept(@NonNull List<CryptoCurrency> cryptoCurrencies) throws Exception {
                        mCryptoCurrencyData.addAll(cryptoCurrencies);
                    }
                });
    }

    @Override
    public Observable<List<CryptoCurrency>> loadDataFromMemory() {
        if(mCryptoCurrencyData.size() > 0 && isDataUptoDate()) {
            return Observable.just(mCryptoCurrencyData);
        }
        else {
            mTimeStamp = System.currentTimeMillis();
            mCryptoCurrencyData.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<List<CryptoCurrency>> loadData() {
        return loadDataFromMemory().switchIfEmpty(loadDataFromNetwork(10));
    }

    @Override
    public Observable<Alert> loadAlertsFromCache() {
        return mAlertsCacheService.getAlertsFromCache();
    }

    @Override
    public Observable saveAlert(Alert alert) {
        return mAlertsCacheService.saveAlert(alert);
    }

    @Override
    public Observable<Alert> getAlert(String guid) {
        return mAlertsCacheService.getAlert(guid);
    }

    @Override
    public Observable deleteAlert(String guid) {
        return mAlertsCacheService.deleteAlert(guid);
    }
}
