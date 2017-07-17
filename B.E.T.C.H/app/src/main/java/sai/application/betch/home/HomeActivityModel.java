package sai.application.betch.home;

import rx.Observable;

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
        return null;
    }
}
