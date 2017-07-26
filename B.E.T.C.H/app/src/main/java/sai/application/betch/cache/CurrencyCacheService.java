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
        List<Currency> currencies = Currency.listAll(Currency.class);
        if(currencies.size() == 0) {
            for (Currency currency : cryptoCurrencies) {
                currency.save();
            }
        }
        else {
            for(Currency newCurrency : cryptoCurrencies) {
                boolean isNewCurrency = true;
                for(Currency currency : currencies) {
                    if(currency.getSymbol().equalsIgnoreCase(newCurrency.getSymbol())) {
                        currency.setPercentChange1h(newCurrency.getPercentChange1h());
                        currency.setPriceUsd(newCurrency.getPriceUsd());
                        //TODO ;; In the future update all the fields
                        currency.save();
                        isNewCurrency = false;
                        break;
                    }
                }
                if(isNewCurrency)
                    newCurrency.save();
            }
        }
        return Observable.empty();
    }
}
