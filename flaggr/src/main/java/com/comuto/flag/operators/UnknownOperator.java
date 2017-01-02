package com.comuto.flag.operators;

import android.support.annotation.Nullable;
import com.comuto.flag.model.Flag;

/***
 * This operator is used when Flaggr don't know an operator
 * For now supported operators are : Inset / Module / Percentage
 */
public class UnknownOperator extends Operator {

    public UnknownOperator(String name) {
        super(name);
    }

    @Override
    public Flag.FlagResultStatus appliesTo(@Nullable Object value) {
        return Flag.FlagResultStatus.UNKNOWN;
    }
}
