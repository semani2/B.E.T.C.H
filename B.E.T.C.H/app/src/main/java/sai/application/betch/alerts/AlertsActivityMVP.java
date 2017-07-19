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

        void showCreateAlertDialog();

        void clearData();

        void showMessage(String msg);

        void showHomeActivity();

        void toggleListVisibility(boolean shouldShowList);

        void toggleBottomSheetBehavior(boolean shouldExpand);
    }

    interface Model{
        Observable<AlertsViewModel> data();

        Observable saveAlert(Alert alert);

        Observable<Alert> getAlert(String guid);

        Observable deleteAlert(String guid);

        Observable<CurrencyViewModel> currencyData();
    }

    interface Presenter{
        void refreshPerformed();

        void loadData();

        void rxUnsubscribe();

        void rxDestroy();

        void setView(AlertsActivityMVP.View view);

        void handleFABClicked();

        void toggleListVisibility(List<AlertsViewModel> data);

        void handleCloseBottomSheetButtonClicked();
    }
}
