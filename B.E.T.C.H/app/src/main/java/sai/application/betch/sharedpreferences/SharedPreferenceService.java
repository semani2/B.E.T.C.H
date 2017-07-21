package sai.application.betch.sharedpreferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sai on 7/20/17.
 */

public class SharedPreferenceService implements ISharedPreferenceService {

    private Context context;
    private static final String SP_NAME = "sai.application.betch.preferences";

    public SharedPreferenceService(Application application) {
        this.context = application;
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }
}
