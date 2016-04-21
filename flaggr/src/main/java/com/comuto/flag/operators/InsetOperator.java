package com.comuto.flag.operators;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Operator that check if a value is in a List of values
 * @param <T> : The Type of the value and the list of values
 */
public class InsetOperator<T> extends Operator<T>{

    private final List<T> values;

    public InsetOperator(String name, List<T> values) {
        super(name);
        this.values = values;
    }

    /**
     * @param value the value to check
     * @return true if the value is in the values list, false otherwise
     */
    @Override
    public boolean appliesTo(@Nullable T value) {
        if (null != values) {
            for (T v : values) {
                if (v.equals(value))
                    return true;
            }
        }
        return false;
    }
}
