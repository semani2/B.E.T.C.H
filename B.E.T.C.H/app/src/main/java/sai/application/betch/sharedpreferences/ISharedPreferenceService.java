package sai.application.betch.sharedpreferences;

/**
 * Created by sai on 7/20/17.
 */

public interface ISharedPreferenceService {
    void saveBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defValue);
}
