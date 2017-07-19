package sai.application.betch.alerts;

import io.reactivex.Observable;
import io.reactivex.Single;
import sai.application.betch.cache.cachemodel.Alert;

/**
 * Created by sai on 7/18/17.
 */

public interface AlertsActivityMVP {

    interface View{
        void updateData(AlertsViewModel viewModel);

        void showCreateAlertDialog();

        void clearData();

        void showMessage(String msg);

        void showHomeActivity();
    }

    interface Model{
        Observable<AlertsViewModel> data();

        Observable saveAlert(Alert alert);

        Single<Alert> getAlert(String guid);

        Observable deleteAlert(String guid);
    }

    interface Presenter{
        void refreshPerformed();

        void loadData();

        void rxUnsubscribe();

        void rxDestroy();

        void setView(AlertsActivityMVP.View view);

        void handleFABClicked();
    }
}
