package com.comuto.flag;

import com.comuto.flag.model.Flag;
import com.comuto.flag.model.FlagContextInterface;
import com.comuto.flag.strategy.AffirmativeStrategy;
import com.comuto.flag.strategy.MajorityStrategy;
import com.comuto.flag.strategy.Strategy;
import com.comuto.flag.strategy.UnanimousStrategy;

/**
 * Manager to handle strategy call and cache
 */
public class FlaggrManager {

    private static Strategy strategy;

    public static boolean isActivated(Flag flag, FlagContextInterface context, boolean defaultValue) {
        switch (flag.getStatus()) {
            case Flag.ALWAYS_ACTIVE:
                return true;
            case Flag.INACTIVE:
                return false;
            case Flag.CONDITIONALLY_ACTIVE:
                return checkConditions(flag, context);
        }
        return defaultValue;
    }


    public static boolean isActivated(Flag flag, FlagContextInterface context) {
       return isActivated(flag, context, false);
    }

    /**
     * Instantiate the good strategy
     * Check if the flag is activated
     * Added to the cache
     */
    private static boolean checkConditions(Flag flag, FlagContextInterface context) {
        switch (flag.getStrategy()) {
            case Flag.UNANIMOUS:
                strategy = new UnanimousStrategy();
                break;
            case Flag.MAJORITY:
                strategy = new MajorityStrategy();
                break;
            case Flag.AFFIRMATIVE:
                strategy = new AffirmativeStrategy();
                break;
        }
        return strategy.isFlagActivated(flag, context);
    }

}
