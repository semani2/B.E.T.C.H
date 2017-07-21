package sai.application.betch.cache;

import java.util.List;

import io.reactivex.Observable;
import sai.application.betch.cache.cachemodel.Currency;

/**
 * Created by sai on 7/21/17.
 */

public class CurrencyCacheService implements ICurrencyCacheService {
    @Override
    public Observable<List<Currency>> getCurrencyFromCache() {
        List<Currency> currencies = Currency.listAll(Currency.class);
        return Observable.just(currencies);
    }

    @Override
    public Observable<Currency> getAlert(String currencyId) {
        //TODO to be implemented
        return null;
    }

    @Override
    public Observable saveAllCurrencies(List<Currency> cryptoCurrencies) {
        Currency.deleteAll(Currency.class);
        for(Currency currency : cryptoCurrencies) {
            currency.save();
        }
        return Observable.empty();
    }
}
