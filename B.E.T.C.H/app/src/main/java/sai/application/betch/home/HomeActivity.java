package sai.application.betch.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.application.betch.R;
import sai.application.betch.root.App;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements HomeActivityMVP.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    HomeActivityMVP.Presenter mPresenter;

    @BindView(R.id.currencyRecyclerView)
    RecyclerView mCurrencyRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CurrencyAdapter mCurrencyAdapter;

    private List<CurrencyViewModel> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        mCurrencyAdapter = new CurrencyAdapter(mDataList, this);

        mCurrencyRecyclerView.setAdapter(mCurrencyAdapter);
        mCurrencyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCurrencyRecyclerView.setHasFixedSize(true);
        mCurrencyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSwipeRefreshLayout.setOnRefreshListener(this);

        setupOnItemClick();

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
    }

    @Override
    public void showSettings() {
        // TODO :: Last feature, show settings screen
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
}
