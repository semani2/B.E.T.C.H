package sai.application.betch.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import sai.application.betch.R;

public class HomeActivity extends AppCompatActivity implements HomeActivityMVP.View{

    @Inject
    HomeActivityMVP.Presenter presenter;

    @BindView(R.id.currencyRecyclerView)
    RecyclerView currencyREcyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void updateData(CurrencyViewModel viewModel) {
        // TODO :: Update the list adapter with the viewmodel data
    }

    @Override
    public void showSettings() {
        // TODO :: Last feature, show settings screen
    }
}
