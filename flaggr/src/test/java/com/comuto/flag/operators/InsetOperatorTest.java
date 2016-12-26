package com.comuto.flag.operators;

import com.comuto.flag.model.Flag;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class InsetOperatorTest {

    @Test
    public void testStringInsetOperator() throws Exception {
        List<String> values = new ArrayList<>();
        values.add("1");
        values.add("2");
        values.add("3");
        values.add("4");
        values.add("5");
        InsetOperator<String> insetOperator = new InsetOperator<>("in-set", values);
        assertEquals(Flag.FlagResultStatus.ENABLED, insetOperator.appliesTo("2"));
        assertEquals(Flag.FlagResultStatus.DISABLED, insetOperator.appliesTo("6"));
    }

    @Test
    public void testIntegerInsetOperator() throws Exception {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(5);
        InsetOperator<Integer> insetOperator = new InsetOperator<>("in-set", values);
        assertEquals(Flag.FlagResultStatus.ENABLED, insetOperator.appliesTo(2));
        assertEquals(Flag.FlagResultStatus.DISABLED, insetOperator.appliesTo(6));
    }

    @Test
    public void testEmptyInsetOperator() throws Exception {
        List<Integer> values = new ArrayList<>();
        InsetOperator<Integer> insetOperator = new InsetOperator<>("in-set", values);
        assertEquals(Flag.FlagResultStatus.DISABLED, insetOperator.appliesTo(2));
        assertEquals(Flag.FlagResultStatus.DISABLED, insetOperator.appliesTo(6));
    }

    @Test
    public void testWithNullListInsetOperator() throws Exception {
        InsetOperator<Integer> insetOperator = new InsetOperator<>("in-set", null);
        assertEquals(Flag.FlagResultStatus.DISABLED, insetOperator.appliesTo(2));
        assertEquals(Flag.FlagResultStatus.DISABLED, insetOperator.appliesTo(6));
    }
}
