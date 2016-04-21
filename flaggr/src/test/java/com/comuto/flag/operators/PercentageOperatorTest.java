package com.comuto.flag.operators;

import com.comuto.flag.operators.PercentageOperator;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PercentageOperatorTest {

    @Test
    public void testSimplePercentageOperator() throws Exception {
        PercentageOperator percentageOperator = new PercentageOperator("modulo", 10, 0);
        assertEquals(true, percentageOperator.appliesTo("8"));
        assertEquals(false, percentageOperator.appliesTo("12"));
    }

    @Test
    public void testSimplePercentageWithShiftOperator() throws Exception {
        PercentageOperator percentageOperator = new PercentageOperator("modulo", 10, 5);
        assertEquals(true, percentageOperator.appliesTo("12"));
        assertEquals(false, percentageOperator.appliesTo("20"));
    }
}
