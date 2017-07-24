package sai.application.betch.alerts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.application.betch.R;
import sai.application.betch.alerts.create_alert.Constants;
import sai.application.betch.alerts.create_alert.CreateAlertBottomSheetDialogFragment;
import sai.application.betch.events.AlertSavedEvent;
import sai.application.betch.root.App;
import timber.log.Timber;

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

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    private BottomSheetBehavior bottomSheetBehavior;

    private AlertAdapter mAlertAdapter;

    private List<AlertsViewModel> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        ((App) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        setupLongPress();
    }

    private void setupAlertSwitchToggle() {
        mPresenter.handleAlertSwitchToggle(mAlertAdapter.getAlertSwitchToggle());
    }

    private void setupLongPress() {
        mPresenter.handleLongPress(mAlertAdapter.getLongPress());
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

        mPresenter.promptForRatingIfNeeded();
    }

    private void updateListLayout() {
        mPresenter.toggleListVisibility(mDataList);
    }

    @Override
    public void updateData(AlertsViewModel viewModel) {
        boolean shouldAdd = true;
        Iterator<AlertsViewModel> iterator = mDataList.iterator();
        while(iterator.hasNext()) {
            AlertsViewModel currentVM = iterator.next();
            if(currentVM.getGuid().equalsIgnoreCase(viewModel.getGuid())) {
                shouldAdd = false;
                break;
            }
        }
        if(shouldAdd) {
            mDataList.add(viewModel);
        }
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

    @Override
    public void showDeleteConfirmation(final AlertsViewModel alertsViewModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(getString(R.string.str_delete_alert))
                .setMessage(alertsViewModel.isTimeTrigger() ? getString(R.string.str_time_alert_message, alertsViewModel.getCurrencyName()) :
                getString(R.string.str_price_alert_message, alertsViewModel.getCurrencyName()))
                .setPositiveButton(getString(R.string.str_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Timber.d("delete confirmed for alert");
                        mPresenter.deleteAlert(alertsViewModel);
                        mDataList.remove(alertsViewModel);
                        mAlertAdapter.notifyDataSetChanged();
                        updateListLayout();
                    }
                })
                .setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Timber.d("Alert delete canceled");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void showUserRatingPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.str_enjoying_betch))
                .setPositiveButton(getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Timber.d("Yes for enjoying betch clicked");
                        dialogInterface.dismiss();
                        getAppStoreDialog().show();
                    }
                })
                .setNegativeButton(getString(R.string.str_not_really), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getFeedbackDialog().show();
                        dialogInterface.dismiss();
                    }
                })
                .show();
        mPresenter.saveBoolean(Constants.APP_RATING_REQUESTED, true);
    }

    private AlertDialog.Builder getAppStoreDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.str_how_about_rating))
                .setPositiveButton(getString(R.string.str_ok_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Timber.d("Yes for submitting app store");
                        dialogInterface.dismiss();
                        mPresenter.saveBoolean(Constants.APP_RATING_SUBMITTED, true);
                        // TODO :: open play store link here
                    }
                })
                .setNegativeButton(getString(R.string.str_no_thanks), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mPresenter.saveBoolean(Constants.APP_RATING_SUBMITTED, false);
                    }
                });
        return builder;
    }

    private AlertDialog.Builder getFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.str_give_feedback))
                .setPositiveButton(getString(R.string.str_ok_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Timber.d("Yes for submitting feedback ");
                        dialogInterface.dismiss();
                        mPresenter.handleSendFeedbackRequested();
                    }
                })
                .setNegativeButton(getString(R.string.str_no_thanks), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Timber.d("no for feedback clicked");
                        dialogInterface.dismiss();
                    }
                });
        return builder;
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

}
