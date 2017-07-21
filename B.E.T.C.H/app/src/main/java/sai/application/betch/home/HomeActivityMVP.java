package sai.application.betch.home;


import io.reactivex.Observable;

/**
 * Created by sai on 7/16/17.
 */

public interface HomeActivityMVP {

    interface Model {
        Observable<CurrencyViewModel> data();

        void saveAlarmManagerStarted();

        boolean isAlarmManagerStarted();
    }

    interface View {
        void updateData(CurrencyViewModel viewModel);

        void showSettings();

        void viewIsRefreshing(boolean isBusy);

        void clearData();

        void showMessage(String msg);

        void showAlertActivity();
    }

    interface Presenter {

        void refreshPerformed();

        void loadData(boolean shouldRepeat);

        void rxUnsubscribe();

        void rxDestroy();

        void setView(HomeActivityMVP.View view);

        void handleItemClick(Observable<CurrencyViewModel> observable);

        void handleMenuAlertClicked();

        boolean isAlarmManagerSet();

        void setAlarmManagerStarted();
    }
}
