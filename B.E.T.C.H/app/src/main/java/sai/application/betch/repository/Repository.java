package sai.application.betch.repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import sai.application.betch.network.CryptoCurrencyApiService;
import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/16/17.
 */

public class Repository implements IRepository {

    private CryptoCurrencyApiService mCryptoCurrencyApiService;
    private List<CryptoCurrency> mCryptoCurrencyData;
    private long mTimeStamp;

    private static final long STALE_MS = 30 * 1000;

    public Repository(CryptoCurrencyApiService apiService) {
        this.mCryptoCurrencyApiService = apiService;
        mTimeStamp = System.currentTimeMillis();
        mCryptoCurrencyData = new ArrayList<>();
    }

    private boolean isDataUptoDate() {
        return System.currentTimeMillis() - mTimeStamp < STALE_MS;
    }

    @Override
    public Observable<List<CryptoCurrency>> loadDataFromNetwork() {
        return mCryptoCurrencyApiService.getCurrencyData(10)
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
        return loadDataFromMemory().switchIfEmpty(loadDataFromNetwork());
    }
}
