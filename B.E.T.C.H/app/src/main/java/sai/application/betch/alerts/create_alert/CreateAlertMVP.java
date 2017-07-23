package sai.application.betch.alerts.create_alert;

import io.reactivex.Observable;
import sai.application.betch.home.CurrencyViewModel;

/**
 * Created by sai on 7/19/17.
 */

public interface CreateAlertMVP {
    interface View{

        void updateCurrencyData(CurrencyViewModel viewModel);

        void showMessage(String msg);

        void closeView();

        void toggleTriggerLayouts(boolean shouldShowPriceLayout);

        void toggleCreateAlertButtonEnabled(boolean isEnabled);

        void startTimeAlarm(long minutes);
    }

    interface Presenter{

        void closeDialogClicked();

        void loadCurrencyData();

        void rxUnsubscribe();

        void rxDestroy();

        void setView(CreateAlertMVP.View view);

        void createAlertClicked();

        void handleCurrencySelected(Observable<CurrencyViewModel> observable);

        void handleTriggerTypeSelected(Observable<String> observable);

        void handlePriceEntered(Observable<String> observable);

        void handleAlertTimeSelected(Observable<AlertTime> observable);

        void handleCreateAlertClicked(Observable observable);
    }
}
