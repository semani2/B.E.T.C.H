package sai.application.betch.alerts;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.application.betch.R;
import sai.application.betch.alerts.create_alert.CreateAlertBottomSheetDialogFragment;
import sai.application.betch.events.AlertSavedEvent;
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

    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    private BottomSheetBehavior bottomSheetBehavior;

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

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setPeekHeight(0);
                }
                else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setPeekHeight(350);
                }
                else if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(350);

        setupAlertSwitchToggle();
    }

    private void setupAlertSwitchToggle() {
        mPresenter.handleAlertSwitchToggle(mAlertAdapter.getAlertSwitchToggle());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mPresenter.setView(this);
        mPresenter.loadData();
        updateListLayout();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AlertSavedEvent event) {
        mDataList.clear();
        mPresenter.refreshEventCalled();
    }

    private void updateListLayout() {
        mPresenter.toggleListVisibility(mDataList);
    }

    @Override
    public void updateData(AlertsViewModel viewModel) {
        mDataList.add(viewModel);
        mAlertAdapter.notifyDataSetChanged();
        updateListLayout();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void toggleListVisibility(boolean shouldShowList) {
        mAlertsRecyclerView.setVisibility(shouldShowList ? View.VISIBLE : View.GONE);
        mEmptyLayout.setVisibility(shouldShowList ? View.GONE : View.VISIBLE);
    }

    @Override
    public void toggleBottomSheetBehavior(boolean shouldExpand) {
        if(shouldExpand) {
            CreateAlertBottomSheetDialogFragment bottomSheetDialog = CreateAlertBottomSheetDialogFragment.getInstance();
            bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet");
        }
    }
}
