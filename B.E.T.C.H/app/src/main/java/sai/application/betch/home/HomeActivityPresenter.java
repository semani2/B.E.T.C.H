package sai.application.betch.home;

/**
 * Created by sai on 7/16/17.
 */

public class HomeActivityPresenter implements HomeActivityMVP.Presenter {

    private HomeActivityMVP.Model model;
    private HomeActivityMVP.View view;

    public HomeActivityPresenter(HomeActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void refreshPerformed() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void rxUnsubscribe() {

    }

    @Override
    public void setView(HomeActivityMVP.View view) {
        this.view = view;
    }
}
