package sai.application.betch.home;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import sai.application.betch.network.apimodel.CryptoCurrency;
import sai.application.betch.repository.IRepository;

/**
 * Created by sai on 7/16/17.
 */

public class HomeActivityModel implements HomeActivityMVP.Model {

    private IRepository repository;

    public HomeActivityModel(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<CurrencyViewModel> data() {
        return repository.loadData().concatMap(new Function<List<CryptoCurrency>, ObservableSource<CryptoCurrency>>() {
            @Override
            public ObservableSource<CryptoCurrency> apply(@NonNull List<CryptoCurrency> cryptoCurrencies) throws Exception {
                return Observable.fromIterable(cryptoCurrencies);
            }
        }).concatMap(new Function<CryptoCurrency, ObservableSource<? extends CurrencyViewModel>>() {
            @Override
            public ObservableSource<? extends CurrencyViewModel> apply(@NonNull CryptoCurrency cryptoCurrency) throws Exception {
                CurrencyViewModel currencyViewModel = new CurrencyViewModel();
                currencyViewModel.setFiatCurrency("USD");
                currencyViewModel.setCurrencyName(cryptoCurrency.getName());
                currencyViewModel.setCurrencySymbol(cryptoCurrency.getSymbol());
                currencyViewModel.setCostPerUnit(cryptoCurrency.getPriceUsd());
                currencyViewModel.setGoingUp(Double.valueOf(cryptoCurrency.getPercentChange1h()) >= 0);

                return Observable.just(currencyViewModel);
            }
        });
    }
}
