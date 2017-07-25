package sai.application.betch.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import sai.application.betch.events.NetworkChangeEvent;
import sai.application.betch.helpers.NetworkHelper;

/**
 * Created by sai on 7/25/17.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new NetworkChangeEvent(NetworkHelper.isNetworkAvailable(context)));
    }
}
