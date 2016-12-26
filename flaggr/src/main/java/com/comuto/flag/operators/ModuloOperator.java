package com.comuto.flag.operators;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.comuto.flag.model.Flag;

/**
 * An operator that check if
 * rest == value % module
 */
public class ModuloOperator extends Operator<String> {
    private final int module;
    private final int rest;

    public ModuloOperator(String name, int module, int rest) {
        super(name);
        this.module = module;
        this.rest = rest;
    }

    @Override
    public Flag.FlagResultStatus appliesTo(@Nullable String value) throws NumberFormatException {
        if (TextUtils.isEmpty(value)) {
            return Flag.FlagResultStatus.DISABLED;
        }

        final int val = Integer.parseInt(value);
        int asModule = val % module;

        return asModule == rest ? Flag.FlagResultStatus.ENABLED : Flag.FlagResultStatus.DISABLED;
    }
}
