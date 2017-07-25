package sai.application.betch.events;

/**
 * Created by sai on 7/25/17.
 */

public class NetworkChangeEvent {

    private final boolean isNetworkAvailable;

    public NetworkChangeEvent(boolean isNetworkAvailable) {
        this.isNetworkAvailable = isNetworkAvailable;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }
}
