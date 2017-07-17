package sai.application.betch.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.application.betch.R;
import sai.application.betch.root.App;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements HomeActivityMVP.View{

    @Inject
    HomeActivityMVP.Presenter mPresenter;

    @BindView(R.id.currencyRecyclerView)
    RecyclerView mCurrencyRecyclerView;

    private CurrencyAdapter mCurrencyAdapter;

    private List<CurrencyViewModel> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((App)getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        mCurrencyAdapter = new CurrencyAdapter(mDataList, this);

        mCurrencyRecyclerView.setAdapter(mCurrencyAdapter);
        mCurrencyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCurrencyRecyclerView.setHasFixedSize(true);
        mCurrencyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Timber.d("Activity Created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.rxUnsubscribe();
        mDataList.clear();
        mCurrencyAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(CurrencyViewModel viewModel) {
        mDataList.add(viewModel);
        mCurrencyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSettings() {
        // TODO :: Last feature, show settings screen
    }
}
