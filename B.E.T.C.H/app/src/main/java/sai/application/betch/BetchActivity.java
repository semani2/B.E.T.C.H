package sai.application.betch;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import sai.application.betch.events.NetworkChangeEvent;
import sai.application.betch.receivers.NetworkBroadcastReceiver;

/**
 * Created by sai on 7/25/17.
 */

public abstract class BetchActivity extends AppCompatActivity {

    protected NetworkBroadcastReceiver mNetworkBroadcastReceiver;
    private IntentFilter mIntentFilter = new IntentFilter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mNetworkBroadcastReceiver = new NetworkBroadcastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        registerReceiver(mNetworkBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mNetworkBroadcastReceiver != null) {
            unregisterReceiver(mNetworkBroadcastReceiver);
            mNetworkBroadcastReceiver = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NetworkChangeEvent event) {
        if(!event.isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_LONG).show();
        }
        handleNetworkChange(event);
    }

    protected abstract void handleNetworkChange(NetworkChangeEvent event);
}
