package sai.application.betch.home;

import rx.Observable;
import sai.application.betch.network.apimodel.CryptoCurrency;

/**
 * Created by sai on 7/16/17.
 */

public class Repository implements IRepository {

    @Override
    public Observable<CryptoCurrency> loadDataFromNetwork() {
        return null;
    }

    @Override
    public Observable<CryptoCurrency> loadDataFromMemory() {
        return null;
    }

    @Override
    public Observable<CryptoCurrency> loadData() {
        return null;
    }
}
