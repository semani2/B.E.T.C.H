package sai.application.betch.alerts;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import sai.application.betch.alerts.create_alert.Constants;
import sai.application.betch.analytics.FirebaseHelper;
import timber.log.Timber;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsActivityPresenter implements AlertsActivityMVP.Presenter {

    private AlertsActivityMVP.Model model;
    private AlertsActivityMVP.View view;

    private CompositeDisposable mCacheDisposables = new CompositeDisposable();
    private CompositeDisposable mEventsDisposables = new CompositeDisposable();

    private FirebaseHelper mFirebaseHelper;

    public AlertsActivityPresenter(AlertsActivityMVP.Model model, FirebaseHelper firebaseHelper) {
        this.model = model;
        this.mFirebaseHelper = firebaseHelper;
    }

    @Override
    public void loadData() {
        final DisposableObserver<AlertsViewModel> loadObserver = new DisposableObserver<AlertsViewModel>() {
            @Override
            public void onNext(AlertsViewModel alertsViewModel) {
                Timber.d("Loading alerts from DB!");
                view.updateData(alertsViewModel);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(("Error loading alerts"), e);
            }

            @Override
            public void onComplete() {
                Timber.d("Alert loading complete");
            }
        };
        model.data().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(loadObserver);

        mCacheDisposables.add(loadObserver);
    }

    @Override
    public void rxUnsubscribe() {
        mCacheDisposables.clear();
        mEventsDisposables.clear();
    }

    @Override
    public void rxDestroy() {
        if(!mCacheDisposables.isDisposed()) {
            mCacheDisposables.dispose();
        }

        if(!mEventsDisposables.isDisposed()) {
            mEventsDisposables.dispose();
        }
    }

    @Override
    public void setView(AlertsActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void handleFABClicked() {
        view.toggleBottomSheetBehavior(true);
    }

    @Override
    public void toggleListVisibility(List<AlertsViewModel> data) {
        if(data.size() == 0) {
            view.toggleListVisibility(false);
        }
        else {
            view.toggleListVisibility(true);
        }
    }

    @Override
    public void refreshEventCalled() {
        loadData();
    }

    @Override
    public void handleAlertSwitchToggle(Observable<AlertsViewModel> observer) {
        final DisposableObserver<AlertsViewModel> alertToggleObserver = new DisposableObserver<AlertsViewModel>() {
            @Override
            public void onNext(AlertsViewModel alertsViewModel) {
                Timber.d("Changing alert status for alert " + alertsViewModel.getGuid() + " Active status: " + alertsViewModel.getIsActive());
                model.updateAlertIsActive(alertsViewModel).subscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(alertToggleObserver);

        mEventsDisposables.add(alertToggleObserver);
    }

    @Override
    public void handleLongPress(Observable<AlertsViewModel> observer) {
        final DisposableObserver<AlertsViewModel> longPressObserver = new DisposableObserver<AlertsViewModel>() {
            @Override
            public void onNext(AlertsViewModel alertsViewModel) {
                Timber.d("Alert long pressed");
                view.showDeleteConfirmation(alertsViewModel);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(longPressObserver);

        mEventsDisposables.add(longPressObserver);
    }

    @Override
    public void deleteAlert(final AlertsViewModel alertsViewModel) {
        final DisposableObserver deleteObserver = new DisposableObserver() {
            @Override
            public void onNext(Object o) {
                Timber.d("Alert deleted " + alertsViewModel.getGuid());

            }

            @Override
            public void onError(Throwable e) {
                Timber.e("Error deleting alert" + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
            }
        };
        model.deleteAlert(alertsViewModel.getGuid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(deleteObserver);

        loadData();

        mCacheDisposables.add(deleteObserver);
    }

    @Override
    public void promptForRatingIfNeeded() {
        final boolean userPromptedForRating = model.getBoolean(Constants.APP_RATING_REQUESTED, false);
        final boolean userRatingSubmitted = model.getBoolean(Constants.APP_RATING_SUBMITTED, false);

        model.getTotalAlerts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer alerts) {
                        Timber.d("Total alerts " + alerts);
                        if(alerts == 0) {
                            return;
                        }

                        if(!userPromptedForRating && (alerts % 3 == 0)) {
                            promptUserForRating();
                        }

                        else if(!userRatingSubmitted && (alerts % 3 == 0)) {
                            promptUserForRating();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error getting number of alerts ; " +e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void promptUserForRating() {
        view.showUserRatingPrompt();
    }

    @Override
    public void handleSendFeedbackRequested() {
        mFirebaseHelper.logSendFeedbackEvent();
        view.sendEmailIntent();
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return model.getBoolean(key, defValue);
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        model.saveBoolean(key, value);
    }
}
