package com.comuto.flag.operators;

import android.support.annotation.Nullable;
import com.comuto.flag.model.Flag;

public class UnknownOperator extends Operator {

    public UnknownOperator(String name) {
        super(name);
    }

    @Override
    public Flag.FlagResultStatus appliesTo(@Nullable Object value) {
        return Flag.FlagResultStatus.UNKNOWN;
    }
}
