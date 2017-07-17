package sai.application.betch.home;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by sai on 7/16/17.
 */

public class HomeActivityPresenter implements HomeActivityMVP.Presenter {

    private HomeActivityMVP.Model model;
    private HomeActivityMVP.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    public HomeActivityPresenter(HomeActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void refreshPerformed() {

    }

    @Override
    public void loadData() {
        view.clearData();

        disposable.add(model.data().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<CurrencyViewModel>() {
            @Override
            public void onNext(CurrencyViewModel viewModel) {
                Timber.d("New currency view model fetched : " + viewModel.getCurrencyName());
                view.updateData(viewModel);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("Error loading currency data", e);
            }

            @Override
            public void onComplete() {
                view.viewIsRefreshing(false);
                Timber.i("Loading currency data completed");
            }
        }));
    }

    @Override
    public void rxUnsubscribe() {
        if(!disposable.isDisposed()) {
            disposable.clear();
        }
    }

    @Override
    public void setView(HomeActivityMVP.View view) {
        this.view = view;
    }
}
