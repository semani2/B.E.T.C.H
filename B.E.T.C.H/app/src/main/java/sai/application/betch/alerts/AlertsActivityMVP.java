package sai.application.betch.alerts;

import java.util.List;

import io.reactivex.Observable;
import sai.application.betch.cache.cachemodel.Alert;
import sai.application.betch.home.CurrencyViewModel;

/**
 * Created by sai on 7/18/17.
 */

public interface AlertsActivityMVP {

    interface View{
        void updateData(AlertsViewModel viewModel);

        void showMessage(String msg);

        void toggleListVisibility(boolean shouldShowList);

        void toggleBottomSheetBehavior(boolean shouldExpand);
    }

    interface Model{
        Observable<AlertsViewModel> data();

        Observable saveAlert(Alert alert);

        Observable<Alert> getAlert(String guid);

        Observable deleteAlert(String guid);

        Observable<CurrencyViewModel> currencyData();

        Observable updateAlertIsActive(AlertsViewModel alertsViewModel);

        boolean getBoolean(String key, boolean defValue);

        void saveBoolean(String key, boolean value);
    }

    interface Presenter{
        void loadData();

        void rxUnsubscribe();

        void rxDestroy();

        void setView(AlertsActivityMVP.View view);

        void handleFABClicked();

        void toggleListVisibility(List<AlertsViewModel> data);

        void refreshEventCalled();

        void handleAlertSwitchToggle(Observable<AlertsViewModel> observer);
    }
}
