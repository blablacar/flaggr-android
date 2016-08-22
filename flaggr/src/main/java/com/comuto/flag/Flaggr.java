package com.comuto.flag;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

import com.comuto.flag.model.Flag;
import com.comuto.flag.model.FlagContextInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Feature flags main entry point.
 * <p>
 *  Use {@link #with(Context)} for the global singleton instance.
 * </p>
 */
public class Flaggr {

    private static final String TAG = "Flaggr";

    protected static final String FLAGGR_SHARED_PREFERENCES_PREFIX = "FLAGGR_ID_";
    private static final String FLAGS_PREF_KEY = "FLAGS_KEYS";

    @VisibleForTesting
    static volatile Flaggr singleton;

    @Inject
    FlagsLoader flagsLoader;
    @Inject
    SharedPreferences preferences;

    private List<Flag> flags;
    private final Map<String, Boolean> flagCaches = new HashMap<>(); /* Cache map */
    private String configUrl;
    private final Context context;

    /**
     *  The global default {@link Flaggr} instance.
     * @param context the activty or application context.
     * @return Flaggr instance.
     */
    public static Flaggr with(final Context context) {
        if (null == singleton) {
            synchronized (Flaggr.class) {
                singleton = new Flaggr(context);
            }
        }
        return singleton;
    }

    private Flaggr(Context context) {
        if (null == context) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.context = context.getApplicationContext();
        FlaggrComponent flaggrComponent = FlaggrComponent.Initializer.init(this.context);
        flaggrComponent.inject(this);
    }

    /**
     *  Start a request to download config url.
     * @param configUrl url of json config.
     */
    public void loadConfig(String configUrl) {
        this.configUrl = configUrl;
        downloadConfig(configUrl);
    }

    /**
     * Reload flags for json config.
     */
    public void reloadConfig() {
        downloadConfig(this.configUrl);
    }

    private void downloadConfig(String configUrl) {
        flagCaches.clear();
        try {
            flagsLoader.load(configUrl, new FlagsCallback() {
                @Override
                public void onLoadFlags(String jsonResponse, List<Flag> results) {
                    flags = results;
                    preferences.edit().putString(FLAGS_PREF_KEY, jsonResponse).apply();
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "Exception when loading config", ex);
        }
    }

    /**
     * Method to call to check that a flag is activated
     * @param resId The flag name resource id
     * @param flagContext the context for checking the flag
     * @return true if the flag is activated, false otherwise
     */
    public boolean isActive(@StringRes int resId, FlagContextInterface flagContext) {
        return isActive(context.getString(resId), flagContext);
    }

    /**
     * Method to call to check that a flag is activated
     * @param flagName The flag name
     * @param flagContext the context for checking the flag
     * @return true if the flag is activated, false otherwise
     */
    public boolean isActive(String flagName, FlagContextInterface flagContext) {
        return isActive(flagName, flagContext, false);
    }

    /**
     * Method to call to check that a flag is activated
     * @param flagName The flag name
     * @param flagContext the context for checking the flag
     * @param defaultValue The default value you want if the flag is not founded or an error happen when checking
     * for the flag
     * @return true if the flag is activated, false otherwise
     */
    public boolean isActive(String flagName, FlagContextInterface flagContext, boolean defaultValue) {
        if (TextUtils.isEmpty(flagName)) {
            return defaultValue;
        }
        /** Check if the flag is in the cache list, return the result */
        if (flagCaches.containsKey(flagName))
            return flagCaches.get(flagName);

        /** CHeck the flag status **/
        if (null != flags && null != flagContext) {
            for (Flag flag : flags) {
                if (null != flag && null != flag.getName() && flag.getName().equals(flagName)) {
                    boolean isActivated = FlaggrManager.isActivated(flag, flagContext, defaultValue);
                    flagCaches.put(flagName, isActivated);
                    return isActivated;
                }
            }
        }
        Log.w(TAG, "The flag "+ flagName +" is not found.");
        return defaultValue;
    }

    private String getPreferenceName(String flagName) {
        return FLAGGR_SHARED_PREFERENCES_PREFIX + flagName;
    }

}
