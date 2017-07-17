package sai.application.betch.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import sai.application.betch.R;

public class HomeActivity extends AppCompatActivity implements HomeActivityMVP.View{

    @Inject
    HomeActivityMVP.Presenter presenter;

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
