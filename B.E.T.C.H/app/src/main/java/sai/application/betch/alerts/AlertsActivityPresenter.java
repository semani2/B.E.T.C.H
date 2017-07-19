package sai.application.betch.alerts;

import com.evernote.android.job.JobManager;

/**
 * Created by sai on 7/18/17.
 */

public class AlertsActivityPresenter implements AlertsActivityMVP.Presenter {

    private AlertsActivityMVP.Model model;
    private JobManager manager;

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

    }

    @Override
    public void handleFABClicked() {

    }
}
