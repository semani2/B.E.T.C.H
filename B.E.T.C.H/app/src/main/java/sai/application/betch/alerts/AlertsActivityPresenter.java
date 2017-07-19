package sai.application.betch.alerts;

import com.evernote.android.job.JobManager;

import java.util.List;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsActivityPresenter implements AlertsActivityMVP.Presenter {

    private AlertsActivityMVP.Model model;
    private JobManager manager;
    private AlertsActivityMVP.View view;

    public AlertsActivityPresenter(AlertsActivityMVP.Model model, JobManager jobManager) {
        this.model = model;
        this.manager = jobManager;
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
    public void rxDestroy() {

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
