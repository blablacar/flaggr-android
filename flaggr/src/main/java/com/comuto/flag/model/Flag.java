package com.comuto.flag.model;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Represent a Flag (Toggle)
 * A flag has :
 * A Name
 * A list of conditions
 * A status
 * A strategy
 * @see Condition
 * @see com.comuto.flag.strategy.Strategy
 *
 */
public class Flag {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        ALWAYS_ACTIVE, // The Toggle is always activated
        CONDITIONALLY_ACTIVE, // The toggle is activated with many conditions
        INACTIVE // The toggle is always deactivated
    })
    public @interface ToggleStatus {}
    public static final String CONDITIONALLY_ACTIVE = "conditionally-active";
    public static final String ALWAYS_ACTIVE = "always-active";
    public static final String INACTIVE = "inactive";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
        UNANIMOUS,
        AFFIRMATIVE,
        MAJORITY
    })
    public @interface ToggleStrategy {}
    public static final String UNANIMOUS = "unanimous";
    public static final String AFFIRMATIVE = "affirmative";
    public static final String MAJORITY = "majority";

    private String name;

    private List<Condition> conditions;

    @ToggleStatus private String status;

    @ToggleStrategy private String strategy;

    public Flag(String name, List<Condition> conditions, String status, String strategy) {
        this.name = name;
        this.conditions = conditions;
        this.status = status;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public @ToggleStatus String getStatus() {
        return status;
    }

    public void setStatus(@ToggleStatus String status) {
        this.status = status;
    }

    public @ToggleStrategy String getStrategy() {
        return strategy;
    }

    public void setStrategy(@ToggleStrategy String strategy) {
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "name='" + name + '\'' +
                ", conditions=" + conditions +
                ", status='" + status + '\'' +
                ", strategy='" + strategy + '\'' +
                '}';
    }
}
