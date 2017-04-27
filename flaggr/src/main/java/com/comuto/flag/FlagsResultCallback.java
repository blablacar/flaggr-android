package com.comuto.flag;

import com.comuto.flag.model.Flag;
import java.util.List;

/**
 * Callback for loading feature flags.
 */
public interface FlagsResultCallback {
    /**
     * Called when the flags are loaded
     */
    public void onFlagLoaded(List<Flag> flags);

    /**
     * If a error happen during the loading of the flags
     */
    public void onError(Exception ex);

    /**
     * When the action is completed
     */
    public void onComplete();
}
