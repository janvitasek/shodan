package com.trioptimum.shodan.facade.service;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.intercept.api.InterceptionRuleBuilder;
import com.trioptimum.shodan.intercept.service.Interception;
import com.trioptimum.shodan.lookup.api.LookupRuleBuilder;
import com.trioptimum.shodan.matcher.service.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 1.12.12
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSettings implements Settings {

    private Configuration configuration;

    protected abstract void configure();

    public void configure(Configuration configuration) {
        this.configuration = configuration;
        try {
            configure();
        } finally {
            this.configuration = null;
        }
    }

    protected LookupRuleBuilder bind(Matcher<? super CallablePoint> destinationMatcher) {
        return configuration.bind(destinationMatcher);
    }

    protected InterceptionRuleBuilder intercept(Interception interception) {
        return configuration.intercept(interception);
    }
}
