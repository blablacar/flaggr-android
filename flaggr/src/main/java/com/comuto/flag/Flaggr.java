package com.comuto.flag;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.comuto.flag.model.Flag;
import com.comuto.flag.model.FlagContextInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * Feature flags main entry point.
 * <p>
 * Use {@link #with(Context)} for the global singleton instance.
 * </p>
 */
public class Flaggr {

    private static final String TAG = "Flaggr";

    protected static final String FLAGGR_SHARED_PREFERENCES_PREFIX = "FLAGGR_PREF_ID_";
    private static final String FLAGS_PREF_KEY = "FLAGS_KEYS";

    @VisibleForTesting static volatile Flaggr singleton;

    @Inject FlagsLoader flagsLoader;
    @Inject SharedPreferences preferences;
    @Inject Gson gson;

    private List<Flag> flags;
    private final Map<String, Flag.FlagResultStatus> flagCaches = new HashMap<>(); /* Cache map */
    private String configUrl;
    private String defaultFlagsFileName;
    private final Context context;

    /**
     * The global default {@link Flaggr} instance.
     *
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
     * Start a request to download config url.
     *
     * @param configUrl url of json config.
     */
    public void loadConfig(String configUrl, String defaultFlagsFileName) {
        this.configUrl = configUrl;
        this.defaultFlagsFileName = defaultFlagsFileName;
        downloadConfig(configUrl, defaultFlagsFileName);
    }

    /**
     * Reload flags for json config.
     */
    public void reloadConfig() {
        downloadConfig(this.configUrl, this.defaultFlagsFileName);
    }

    private void downloadConfig(String configUrl, final String defaultFlagsFileName) {
        flagCaches.clear();
        try {
            loadLocalFlags(defaultFlagsFileName);
            flagsLoader.load(configUrl, new FlagsCallback() {
                @Override
                public void onLoadFlags(String jsonResponse, List<Flag> results) {
                    parseFlagJsonResults(jsonResponse, results);
                }

                @Override
                public void onError() {
                    loadLocalFlags(defaultFlagsFileName);
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "Exception when loading config", ex);
        }
    }

    @VisibleForTesting
    public void parseFlagJsonResults(String jsonResponse, List<Flag> results) {
        if (jsonResponse == null || jsonResponse.isEmpty() || results == null || results.isEmpty())
            return;
        flags = results;
        preferences.edit().putString(FLAGS_PREF_KEY, jsonResponse).apply();
    }

    private void loadLocalFlags(final String defaultFlagsFileName) {
        String jsonResponse = readJSONFromAsset(defaultFlagsFileName);

        Type listType = new TypeToken<List<Flag>>() {}.getType();
        flags = gson.fromJson(jsonResponse, listType);
        preferences.edit().putString(FLAGS_PREF_KEY, jsonResponse).apply();
    }

    @Nullable
    public String readJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Method to call to check that a flag is activated
     *
     * @param resId The flag name resource id
     * @param flagContext the context for checking the flag
     * @return true if the flag is activated, false otherwise
     */
    public Flag.FlagResultStatus isActive(@StringRes int resId, FlagContextInterface flagContext) {
        return isActive(context.getString(resId), flagContext);
    }

    /**
     * Method to call to check that a flag is activated
     *
     * @param flagName The flag name
     * @param flagContext the context for checking the flag
     * @return true if the flag is activated, false otherwise
     */
    public Flag.FlagResultStatus isActive(String flagName, FlagContextInterface flagContext) {
        return isActive(flagName, flagContext, false);
    }

    /**
     * Method to call to check that a flag is activated
     *
     * @param flagName The flag name
     * @param flagContext the context for checking the flag
     * @param defaultValue The default value you want if the flag is not founded or an error happen when checking
     * for the flag
     * @return true if the flag is activated, false otherwise
     */
    public Flag.FlagResultStatus isActive(@NonNull String flagName, FlagContextInterface flagContext, boolean defaultValue) {
        if (StringUtils.isEmpty(flagName)) {
            return Flag.FlagResultStatus.UNKNOWN;
        }
        /** Check if the flag is in the cache list, return the result */
        if (flagCaches.containsKey(flagName)) return flagCaches.get(flagName);

        /** CHeck the flag status **/
        if (null != flags && null != flagContext) {
            for (Flag flag : flags) {
                if (null != flag && null != flag.getName() && flag.getName().equals(flagName)) {
                    Flag.FlagResultStatus flagResultStatus = FlaggrManager.getFlagStatus(flag, flagContext, defaultValue);
                    flagCaches.put(flagName, flagResultStatus);
                    return flagResultStatus;
                }
            }
        }
        Log.w(TAG, "The flag " + flagName + " is not found.");
        return Flag.FlagResultStatus.UNKNOWN;
    }

    private String getPreferenceName(String flagName) {
        return FLAGGR_SHARED_PREFERENCES_PREFIX + flagName;
    }
}
