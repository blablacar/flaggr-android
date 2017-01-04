package com.comuto.flag.strategy;

import android.support.annotation.NonNull;

import com.comuto.flag.model.Condition;
import com.comuto.flag.model.Flag;
import com.comuto.flag.model.FlagContextInterface;

import java.util.List;

/**
 * Strategy that check if all conditions are valid
 * @see com.comuto.flag.operators.Operator
 */
public class UnanimousStrategy implements Strategy {

    /***
     * @param flag The flag to check
     * @param context The context used to do operation
     * @return true if a majority of condition are valid, false otherwise
     */
    @Override
    public Flag.FlagResultStatus getFlagStatus(Flag flag, @NonNull FlagContextInterface context) {
        List<Condition> conditionList = flag.getConditions();
        if (null != conditionList && conditionList.size() > 0) {
            for (Condition condition : conditionList) {
                if (null != condition
                    && null != condition.getOperator()
                    && condition.getOperator().appliesTo(context.getValue(condition.getKey())) == Flag.FlagResultStatus.DISABLED) {
                    return Flag.FlagResultStatus.DISABLED;
                }
            }
        }

        return Flag.FlagResultStatus.ENABLED;
    }
}
