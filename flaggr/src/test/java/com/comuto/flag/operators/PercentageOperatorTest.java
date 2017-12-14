package com.comuto.flag.operators;

import com.comuto.flag.model.Flag;
import com.comuto.flag.operators.PercentageOperator;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PercentageOperatorTest {

    @Test
    public void testSimplePercentageOperator() throws Exception {
        PercentageOperator percentageOperator = new PercentageOperator("modulo", 10, 0);
        assertEquals(Flag.FlagResultStatus.ENABLED, percentageOperator.appliesTo("8"));
        assertEquals(Flag.FlagResultStatus.DISABLED, percentageOperator.appliesTo("12"));
    }

    @Test
    public void testSimplePercentageWithShiftOperator() throws Exception {
        PercentageOperator percentageOperator = new PercentageOperator("modulo", 10, 5);
        assertEquals(Flag.FlagResultStatus.ENABLED, percentageOperator.appliesTo("12"));
        assertEquals(Flag.FlagResultStatus.DISABLED, percentageOperator.appliesTo("20"));
    }

    @Test
    public void testPercentageWithoutRegistrationValue() throws Exception {
        PercentageOperator percentageOperator = new PercentageOperator("modulo", 10, 5);
        assertEquals(Flag.FlagResultStatus.DISABLED, percentageOperator.appliesTo("0"));
    }
}
