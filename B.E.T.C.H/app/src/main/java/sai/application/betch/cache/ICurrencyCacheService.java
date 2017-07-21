package sai.application.betch.cache;

import java.util.List;

import io.reactivex.Observable;
import sai.application.betch.cache.cachemodel.Currency;

/**
 * Created by sai on 7/21/17.
 */

public interface ICurrencyCacheService {
    Observable<List<Currency>> getCurrencyFromCache();

    Observable<Currency> getAlert(String currencyId);

    Observable saveAllCurrencies(List<Currency> cryptoCurrencies);
}
