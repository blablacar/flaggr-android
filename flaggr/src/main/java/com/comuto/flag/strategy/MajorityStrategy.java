package com.comuto.flag.strategy;

import android.support.annotation.NonNull;

import com.comuto.flag.model.Condition;
import com.comuto.flag.model.Flag;
import com.comuto.flag.model.FlagContextInterface;

import java.util.List;

/**
 * Strategy that check if a majority of conditions are valid
 *
 * @see com.comuto.flag.operators.Operator
 */
public class MajorityStrategy implements Strategy {

    /***
     * @param flag The flag to check
     * @param context The context used to do operation
     * @return true if a majority of conditions are validated (> half), false otherwise
     */
    @Override
    public Flag.FlagResultStatus getFlagStatus(Flag flag, @NonNull FlagContextInterface context) {
        int verifiedCondition = 0;
        List<Condition> conditionList = flag.getConditions();
        if (null != conditionList) {
            for (Condition condition : conditionList) {
                if (null != condition
                    && null != condition.getOperator()
                    && condition.getOperator().appliesTo(context.getValue(condition.getKey())) == Flag.FlagResultStatus.ENABLED) {
                    ++verifiedCondition;
                }
            }

            if (verifiedCondition > (conditionList.size() / 2)) {
                return Flag.FlagResultStatus.ENABLED;
            }
        }

        return Flag.FlagResultStatus.DISABLED;
    }
}
