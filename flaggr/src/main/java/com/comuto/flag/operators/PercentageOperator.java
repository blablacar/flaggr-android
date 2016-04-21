package com.comuto.flag.operators;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * An operator that applied to toggle to
 * a percentage of users
 */
public class PercentageOperator extends Operator<String> {

    private static final int MAX_PERCENTAGE = 100;
    private final int percentage;
    private final int shift;

    public PercentageOperator(String name, int percentage, int shift) {
        super(name);
        this.percentage = percentage;
        this.shift = shift;
    }

    @Override
    public boolean appliesTo(@Nullable String value) throws NumberFormatException {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        final int val = Integer.parseInt(value);
        final int asPercentage = val % MAX_PERCENTAGE;

        return asPercentage >= shift && asPercentage < (percentage + shift);
    }
}
