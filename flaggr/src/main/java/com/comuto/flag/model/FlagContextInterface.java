package com.comuto.flag.model;

import java.util.Map;

/**
 * This interface is used to provide to the flaggr a context
 * This context will be used to evaluate if the flag is active or not
 * You context class must implement this interface
 * @param <T> The type of data used for the context values
 */
public interface FlagContextInterface<T> {

    Map<String, T> getFlagContext();

    T getValue(String key);
}
