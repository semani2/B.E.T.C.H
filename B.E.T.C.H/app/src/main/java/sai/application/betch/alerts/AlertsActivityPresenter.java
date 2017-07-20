package sai.application.betch.alerts;

import com.evernote.android.job.JobManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsActivityPresenter implements AlertsActivityMVP.Presenter {

    private AlertsActivityMVP.Model model;
    private JobManager manager;
    private AlertsActivityMVP.View view;

    private CompositeDisposable mCacheDisposables = new CompositeDisposable();

    public AlertsActivityPresenter(AlertsActivityMVP.Model model, JobManager jobManager) {
        this.model = model;
        this.manager = jobManager;
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
    }

    @Override
    public void rxDestroy() {
        if(!mCacheDisposables.isDisposed()) {
            mCacheDisposables.dispose();
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
    public void handleCloseBottomSheetButtonClicked() {
        view.toggleBottomSheetBehavior(false);
    }
}
