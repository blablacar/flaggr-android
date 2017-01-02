package com.comuto.flag;

import com.comuto.flag.model.Condition;
import com.comuto.flag.model.Flag;
import com.comuto.flag.operators.InsetOperator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class UnknownFlagTest {

    private String flag = "["
        + "{"
        + "\"name\": \"flag1\","
        + "\"conditions\": ["
        + "{"
        + "\"name\": \"operator-condition\","
        + "\"key\": \"user_locale\","
        + "\"operator\": {"
        + "\"name\": \"in-set\","
        + "\"values\": ["
        + "\"pl_PL\","
        + "\"fr_BE\","
        + "\"fr_NL\","
        + "\"fr_FR\""
        + "]"
        + "}"
        + "}"
        + "],"
        + "\"status\": \"conditionally-active\","
        + "\"strategy\": \"affirmative\""
        + "}"
        + "]";
    private List<Flag> flagList;

    @Before
    public void setUp() {
        flagList = new ArrayList<>();
        Condition condition = new Condition();
        List<String> values = new ArrayList<>();
        values.add("pl_PL");
        values.add("fr_BE");
        values.add("fr_NL");
        values.add("fr_FR");
        InsetOperator<String> insetOperator = new InsetOperator<>("in-set", values);
        condition.setName("in-set");
        condition.setKey("user_locale");
        condition.setOperator(insetOperator);
        List<Condition> conditions = new ArrayList<>();
        conditions.add(condition);
        flagList.add(new Flag("flag1",
            conditions,
            "conditionally-active",
            "affirmative"
        ));
        Flaggr.with(RuntimeEnvironment.application).parseFlagJsonResults(flag, flagList);
    }

    @Test
    public void testUnknownFlags() throws Exception {
        assertEquals(Flag.FlagResultStatus.UNKNOWN,
            Flaggr.with(RuntimeEnvironment.application).isActive(null, null, false));
        assertEquals(Flag.FlagResultStatus.UNKNOWN,
            Flaggr.with(RuntimeEnvironment.application).isActive("", null, false));
        assertEquals(Flag.FlagResultStatus.UNKNOWN,
            Flaggr.with(RuntimeEnvironment.application).isActive("test", null, false));
    }
}
