package com.comuto.flag.operators;

import android.support.annotation.Nullable;
import android.text.TextUtils;

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
    public boolean appliesTo(@Nullable String value) throws NumberFormatException {
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        final int val = Integer.parseInt(value);
        int asModule = val % module;

        return asModule == rest;
    }
}
