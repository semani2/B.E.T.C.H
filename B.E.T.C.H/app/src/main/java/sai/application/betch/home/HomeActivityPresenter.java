package sai.application.betch.home;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sai.application.betch.analytics.FirebaseHelper;
import timber.log.Timber;

/**
 * Created by sai on 7/16/17.
 */

public class HomeActivityPresenter implements HomeActivityMVP.Presenter {

    private HomeActivityMVP.Model model;
    private HomeActivityMVP.View view;
    private CompositeDisposable networkDisposable = new CompositeDisposable();
    private CompositeDisposable eventDisposable = new CompositeDisposable();
    private FirebaseHelper mFirebaseHeler;

    public HomeActivityPresenter(HomeActivityMVP.Model model, FirebaseHelper firebaseHelper) {
        this.model = model;
        this.mFirebaseHeler = firebaseHelper;
    }

    @Override
    public void refreshPerformed() {

    }

    @Override
    public void loadData(final boolean shouldRepeat) {
        final DisposableObserver<CurrencyViewModel> dObserver = new DisposableObserver<CurrencyViewModel>() {
            @Override
            public void onNext(CurrencyViewModel viewModel) {
                if (!shouldRepeat) {
                    view.viewIsRefreshing(false);
                }
                Timber.d("New currency view model fetched : " + viewModel.getCurrencyName() + " Price: " + viewModel.getCostPerUnit());
                view.updateData(viewModel);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("Error loading currency data", e);
            }

            @Override
            public void onComplete() {
                Timber.i("Loading currency data completed");
            }
        };
        DisposableObserver<CurrencyViewModel> d;
        if(!shouldRepeat) {
            d = forceLoadData(dObserver);
        }
        else {
            d = model.data().subscribeOn(Schedulers.io())
                    .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                            return objectObservable.delay(30, TimeUnit.SECONDS);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(dObserver);
        }
        networkDisposable.add(d);

    }

    private DisposableObserver<CurrencyViewModel> forceLoadData(DisposableObserver<CurrencyViewModel> d) {
        return model.data().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(d);
    }

    @Override
    public void rxUnsubscribe() {
        if(networkDisposable != null && !networkDisposable.isDisposed()) {
            networkDisposable.clear();
        }
    }

    @Override
    public void rxDestroy() {
        if(networkDisposable != null && !networkDisposable.isDisposed()) {
            networkDisposable.dispose();
        }
        if(eventDisposable != null && !eventDisposable.isDisposed()) {
            eventDisposable.dispose();
        }
    }

    @Override
    public void setView(HomeActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void handleItemClick(Observable<CurrencyViewModel> observable) {
        final DisposableObserver<CurrencyViewModel> onClickDisposableObserver = new DisposableObserver<CurrencyViewModel>() {
            @Override
            public void onNext(CurrencyViewModel viewModel) {
                // We can take to a detail page here in the future
                Timber.d("Clicked on list item" + viewModel.getId());
                view.showMessage(viewModel.getCurrencyName() + " :" + viewModel.getCostPerUnit());


            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onComplete() {
                Timber.d("Done with click events!!");
            }
        };
        observable.observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(onClickDisposableObserver);

        eventDisposable.add(onClickDisposableObserver);
    }

    @Override
    public void handleMenuAlertClicked() {
        view.showAlertActivity();
        mFirebaseHeler.logViewAlertsEvent();
    }

    @Override
    public void handleMenuSendFeedbackClicked() {
        view.sendEmailIntent();
        mFirebaseHeler.logSendFeedbackEvent();
    }

    @Override
    public boolean isAlarmManagerSet() {
        return model.isAlarmManagerStarted();
    }

    @Override
    public void setAlarmManagerStarted() {
        model.saveAlarmManagerStarted();
    }

    @Override
    public void toggleListVisibility(List<CurrencyViewModel> list) {
        if(list.size() == 0) {
            view.setToggleListVisibility(false);
        }
        else {
            view.setToggleListVisibility(true);
        }
    }
}
