package sai.application.betch.home;


import io.reactivex.Observable;

/**
 * Created by sai on 7/16/17.
 */

public interface HomeActivityMVP {

    interface Model {
        Observable<CurrencyViewModel> data();
    }

    interface View {
        void updateData(CurrencyViewModel viewModel);

        void showSettings();

        void viewIsRefreshing(boolean isBusy);

        void clearData();
    }

    interface Presenter {

        void refreshPerformed();

        void loadData();

        void rxUnsubscribe();

        void setView(HomeActivityMVP.View view);
    }
}
