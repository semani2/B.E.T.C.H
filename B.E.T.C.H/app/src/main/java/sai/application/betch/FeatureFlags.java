package sai.application.betch;

/**
 * Created by sai on 7/21/17.
 */

public class FeatureFlags {

    private static boolean isPeriodicAlarmEnabled = true;

    public static boolean isPeriodicAlarmEnabled() {
        return isPeriodicAlarmEnabled;
    }
}
