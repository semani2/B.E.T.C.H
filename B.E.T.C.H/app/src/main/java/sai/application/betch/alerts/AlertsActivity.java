package sai.application.betch.alerts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.application.betch.R;
import sai.application.betch.root.App;

public class AlertsActivity extends AppCompatActivity implements AlertsActivityMVP.View{

    @Inject
    AlertsActivityMVP.Presenter mPresenter;

    @BindView(R.id.alertsRecyclerView)
    RecyclerView mAlertsRecyclerView;

    @BindView(R.id.emptyLayout)
    RelativeLayout mEmptyLayout;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private AlertAdapter mAlertAdapter;

    private List<AlertsViewModel> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        mAlertAdapter = new AlertAdapter(this, mDataList);

        mAlertsRecyclerView.setAdapter(mAlertAdapter);
        mAlertsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAlertsRecyclerView.setHasFixedSize(true);
        mAlertsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.handleFABClicked();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.loadData();
        updateListLayout();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.rxUnsubscribe();
        mDataList.clear();
        mAlertAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.rxDestroy();
        mDataList.clear();
        mAlertAdapter.notifyDataSetChanged();
    }

    private void updateListLayout() {
        mPresenter.toggleListVisibility(mDataList);
    }

    @Override
    public void updateData(AlertsViewModel viewModel) {
       updateListLayout();
    }

    @Override
    public void showCreateAlertDialog() {

    }

    @Override
    public void clearData() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showHomeActivity() {

    }

    @Override
    public void toggleListVisibility(boolean shouldShowList) {
        mAlertsRecyclerView.setVisibility(shouldShowList ? View.VISIBLE : View.GONE);
        mEmptyLayout.setVisibility(shouldShowList ? View.GONE : View.VISIBLE);
    }
}
