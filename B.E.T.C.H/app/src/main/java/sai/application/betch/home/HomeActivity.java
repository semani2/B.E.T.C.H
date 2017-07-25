package sai.application.betch.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.application.betch.BetchActivity;
import sai.application.betch.FeatureFlags;
import sai.application.betch.R;
import sai.application.betch.alerts.AlertsActivity;
import sai.application.betch.events.NetworkChangeEvent;
import sai.application.betch.root.App;
import sai.application.betch.services.PriceAlertService;
import timber.log.Timber;

public class HomeActivity extends BetchActivity implements HomeActivityMVP.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    HomeActivityMVP.Presenter mPresenter;

    @BindView(R.id.currencyRecyclerView)
    RecyclerView mCurrencyRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @BindView(R.id.emptyLayout)
    RelativeLayout mEmptyLayout;

    private CurrencyAdapter mCurrencyAdapter;

    private List<CurrencyViewModel> mDataList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mCurrencyAdapter = new CurrencyAdapter(mDataList, this);

        mCurrencyRecyclerView.setAdapter(mCurrencyAdapter);
        mCurrencyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCurrencyRecyclerView.setHasFixedSize(true);
        mCurrencyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefreshLayout.setOnRefreshListener(this);

        setupOnItemClick();

        startAlarmManager();

        Timber.d("Activity Created");
    }

    private void setupOnItemClick() {
        mPresenter.handleItemClick(mCurrencyAdapter.getPositionClick());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.loadData(true);
        updateListLayout();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.rxUnsubscribe();
        mDataList.clear();
        mCurrencyAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.rxDestroy();
        mDataList.clear();
        mCurrencyAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(CurrencyViewModel viewModel) {
        Iterator<CurrencyViewModel> iterator = mDataList.iterator();
        boolean isAlreadyAdded = false;
        while (iterator.hasNext()) {
            CurrencyViewModel cvm = iterator.next();
            if (cvm.getId().equalsIgnoreCase(viewModel.getId())) {
                cvm.setCostPerUnit(viewModel.getCostPerUnit());
                cvm.setGoingUp(viewModel.isGoingUp());
                isAlreadyAdded = true;
                break;
            }
        }
        if (!isAlreadyAdded) {
            mDataList.add(viewModel);
        }
        mCurrencyAdapter.notifyDataSetChanged();
        updateListLayout();
    }

    @Override
    public void showSettings() {
        // TODO :: Last feature, show settings screen
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_alerts:
                mPresenter.handleMenuAlertClicked();
                return true;

            case R.id.menu_send_feedback:
                mPresenter.handleMenuSendFeedbackClicked();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void viewIsRefreshing(boolean isBusy) {
        if(mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(isBusy);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.loadData(false);
    }

    @Override
    public void clearData() {
        mDataList.clear();
        mCurrencyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlertActivity() {
        Intent intent = new Intent(this, AlertsActivity.class);
        startActivity(intent);
    }

    @Override
    public void sendEmailIntent() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.str_feedback_email) });
        Email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.str_feedback));
        Email.putExtra(Intent.EXTRA_TEXT, "Dear Chitta Labs," + "");
        startActivity(Intent.createChooser(Email, getString(R.string.menu_alert_send_feedback)));
    }

    @Override
    public void setToggleListVisibility(boolean shouldShowList) {
        mCurrencyRecyclerView.setVisibility(shouldShowList ? View.VISIBLE : View.GONE);
        mEmptyLayout.setVisibility(shouldShowList ? View.GONE : View.VISIBLE);
    }

    private void startAlarmManager() {
        if(mPresenter.isAlarmManagerSet()) {
            return;
        }

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, PriceAlertService.class);
        alarmIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if(FeatureFlags.isPeriodicAlarmEnabled()) {
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), (300 * 100), alarmIntent);
            mPresenter.setAlarmManagerStarted();
        }
    }

    private void updateListLayout() {
        mPresenter.toggleListVisibility(mDataList);
    }

    @Override
    protected void handleNetworkChange(NetworkChangeEvent event) {
        if(null != mPresenter) {
            mPresenter.loadData(true);
        }
    }
}
