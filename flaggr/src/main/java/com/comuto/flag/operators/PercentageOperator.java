package com.comuto.flag.operators;

import android.support.annotation.Nullable;
import com.comuto.flag.model.Flag;
import org.apache.commons.lang3.StringUtils;

import static com.comuto.flag.model.Flag.FlagResultStatus.DISABLED;

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
    public Flag.FlagResultStatus appliesTo(@Nullable String value) throws NumberFormatException {
        if (StringUtils.isEmpty(value)) {
            return DISABLED;
        }
        final int val = Integer.parseInt(value);

        //If there is no registration time
        if (val == 0) {
            return DISABLED;
        }

        final int asPercentage = val % MAX_PERCENTAGE;

        return asPercentage >= shift && asPercentage < (percentage + shift) ? Flag.FlagResultStatus.ENABLED : DISABLED;
    }
}
