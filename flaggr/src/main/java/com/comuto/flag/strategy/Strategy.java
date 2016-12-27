package com.comuto.flag.strategy;

import com.comuto.flag.model.Flag;
import com.comuto.flag.model.FlagContextInterface;

/**
 * An Interface for Strategy
 * All Strategies must implement this class
 * and implement the getFlagStatus method
 */
public interface Strategy {

    /**
     * Check if a flag is activated
     * @param flag The flag to check
     * @param context The context used to do operation
     * @return true if the flag is activated, false otherwise
     */
    Flag.FlagResultStatus getFlagStatus(Flag flag, FlagContextInterface context);
}
