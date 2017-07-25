package sai.application.betch.alerts.create_alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;
import sai.application.betch.R;
import sai.application.betch.events.NetworkChangeEvent;
import sai.application.betch.home.CurrencyViewModel;
import sai.application.betch.jobscheduler.ShowNotificationService;
import sai.application.betch.root.App;

/**
 * Created by sai on 7/19/17.
 */

public class CreateAlertBottomSheetDialogFragment extends BottomSheetDialogFragment implements CreateAlertMVP.View, AdapterView.OnItemSelectedListener{

    @BindView(R.id.closeBottomSheetButton)
    ImageButton mCloseBottomSheetButton;

    @BindView(R.id.currencySpinner)
    Spinner mSelectCurrencySpinner;

    @BindView(R.id.triggerRadioGroup)
    RadioGroup mTriggerRadioGroup;

    @BindView(R.id.enterPriceLayout)
    LinearLayout mEnterPriceLayout;

    @BindView(R.id.priceEditText)
    EditText mPriceEditText;

    @BindView(R.id.selectTimeLayout)
    LinearLayout mSelectTimeLayout;

    @BindView(R.id.alertTimeSpinner)
    Spinner mAlertTimeSpinner;

    @BindView(R.id.createAlertButton)
    TextView createAlertButton;

    @Inject
    CreateAlertMVP.Presenter mPresenter;

    private boolean mIsNetworkAvailable = true;
    private boolean mIsSaveEnabled = false;

    private List<CurrencyViewModel> currencies = new ArrayList<>();
    private ArrayAdapter<CurrencyViewModel> currencySpinnerAdapter;

    private ArrayAdapter<AlertTime> alertTimeArrayAdapter;

    private PublishSubject<CurrencyViewModel> onCurrencySelectedSubject = PublishSubject.create();
    private PublishSubject<AlertTime> onAlertTimeSelectedSubject = PublishSubject.create();
    private PublishSubject<String> onTriggerTypeSelectedSubject = PublishSubject.create();
    private PublishSubject<String> onPriceEnteredSubject = PublishSubject.create();
    private PublishSubject<Boolean> onCreateButtonClickedSuject = PublishSubject.create();

    public static CreateAlertBottomSheetDialogFragment getInstance() {
        return new CreateAlertBottomSheetDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.new_alert_bottom_sheet, container, false);

        ((App) getActivity().getApplication()).getComponent().inject(this);

        ButterKnife.bind(this, view);

        currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, currencies);
        mSelectCurrencySpinner.setAdapter(currencySpinnerAdapter);
        mSelectCurrencySpinner.setOnItemSelectedListener(this);

        alertTimeArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, getAlertTimeData());
        mAlertTimeSpinner.setAdapter(alertTimeArrayAdapter);
        mAlertTimeSpinner.setOnItemSelectedListener(this);

        mPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onPriceEnteredSubject.onNext(editable.toString());
            }
        });

        mTriggerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                if(checkedId == R.id.priceRadioButton) {
                    onTriggerTypeSelectedSubject.onNext(Constants.PRICE_TRIGGER);
                }
                else if(checkedId == R.id.timeRadioButton) {
                    onTriggerTypeSelectedSubject.onNext(Constants.TIME_TRIGGER);
                }
            }
        });


        mCloseBottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.closeDialogClicked();
            }
        });

        createAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateButtonClickedSuject.onNext(true);
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.loadCurrencyData();

        EventBus.getDefault().register(this);

        mPresenter.handleCurrencySelected(onCurrencySelectedSubject);
        mPresenter.handleAlertTimeSelected(onAlertTimeSelectedSubject);
        mPresenter.handlePriceEntered(onPriceEnteredSubject);
        mPresenter.handleTriggerTypeSelected(onTriggerTypeSelectedSubject);
        mPresenter.handleCreateAlertClicked(onCreateButtonClickedSuject);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mPresenter.rxUnsubscribe();
        currencies.clear();
        currencySpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mPresenter.rxDestroy();
        currencies.clear();
        currencySpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCurrencyData(CurrencyViewModel viewModel) {
        currencies.add(viewModel);
        currencySpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeView() {
        dismissAllowingStateLoss();
    }

    @Override
    public void toggleTriggerLayouts(boolean shouldShowPriceLayout) {
        mEnterPriceLayout.setVisibility(shouldShowPriceLayout ? View.VISIBLE : View.GONE);
        mSelectTimeLayout.setVisibility(shouldShowPriceLayout ? View.GONE : View.VISIBLE);
    }

    @Override
    public void toggleCreateAlertButtonEnabled(boolean isEnabled) {
        mIsSaveEnabled = isEnabled;
        createAlertButton.setEnabled(mIsNetworkAvailable && isEnabled);
    }

    @Override
    public void startTimeAlarm(long minutes) {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), ShowNotificationService.class);
        intent.putExtra(Constants.MINUTES_KEY, minutes);
        final int _id = (int) System.currentTimeMillis();
        alarmIntent = PendingIntent.getService(getContext(), _id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + TimeUnit.MINUTES.toMillis(minutes), TimeUnit.MINUTES.toMillis(minutes), alarmIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(adapterView.getId() == R.id.currencySpinner) {
            onCurrencySelectedSubject.onNext((CurrencyViewModel) adapterView.getItemAtPosition(position));
        }
        if(adapterView.getId() == R.id.alertTimeSpinner) {
            onAlertTimeSelectedSubject.onNext((AlertTime) adapterView.getItemAtPosition(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private List<AlertTime> getAlertTimeData() {
        List<AlertTime> alertTimes = new ArrayList<>();
        alertTimes.add(new AlertTime(getString(R.string.str_1_hr), 60));
        alertTimes.add(new AlertTime(getString(R.string.str_2_hr), 120));
        alertTimes.add(new AlertTime(getString(R.string.str_6_hr), 360));
        alertTimes.add(new AlertTime(getString(R.string.str_12_hours), 720));
        alertTimes.add(new AlertTime(getString(R.string.str_24_hours), 1440));

        return alertTimes;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessagEvent(NetworkChangeEvent event) {
        mIsNetworkAvailable = event.isNetworkAvailable();
        createAlertButton.setEnabled(mIsNetworkAvailable && mIsSaveEnabled);
    }
}
