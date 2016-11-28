package com.comuto.flag;

import com.comuto.flag.model.Flag;

import java.util.List;

/**
 * Callback for loading feature flags.
 */
public interface FlagsCallback {
    /**
     * Called after flags are loaded.
     * @param jsonResponse flags json response.
     * @param flags List of parsed flags.
     */
    void onLoadFlags(String jsonResponse, List<Flag> flags);

    /**
     * Called if there is an error
     * The file id not downloaded
     */
    void onError();
}
