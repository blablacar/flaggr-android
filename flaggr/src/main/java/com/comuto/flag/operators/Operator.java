package com.comuto.flag.operators;

import android.support.annotation.Nullable;

/**
 * An abstract operator
 * All the operators must extend this class
 * and implement the appliesTo method
 * @param <T> The type of the values to test
 * @see InsetOperator
 * @see ModuloOperator
 * @see PercentageOperator
 */
public abstract class Operator<T> {
    private final String name;

    public Operator(String name) {
        this.name = name;
    }

    /**
     * This method will check if an operator validates a value
     * Each operator must implement this method
     * @param value the value to validate
     * @return true if the value is validated, false otherwise
     */
    public abstract boolean appliesTo(@Nullable T value);
}
