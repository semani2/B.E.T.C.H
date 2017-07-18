package sai.application.betch.home;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
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
        final DisposableObserver<CurrencyViewModel> d = model.data().subscribeOn(Schedulers.io())
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        return objectObservable.delay(30, TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CurrencyViewModel>() {
                    @Override
                    public void onNext(CurrencyViewModel viewModel) {
                        Timber.d("New currency view model fetched : " + viewModel.getCurrencyName() + " Price: " + viewModel.getCostPerUnit());
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
                });
        disposable.add(d);

    }

    @Override
    public void rxUnsubscribe() {
        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void setView(HomeActivityMVP.View view) {
        this.view = view;
    }
}
