package com.comuto.flag.model;

import com.comuto.flag.operators.Operator;

/**
 * Represent condition to activate the feature toggle
 * @see Operator
 */
public class Condition {
    /**
     * Condition Name
     */
    private String name;
    /**
     * Condition key
     */
    private String key;
    /**
     * The operator used to evaluate the condition
     */
    private Operator operator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
