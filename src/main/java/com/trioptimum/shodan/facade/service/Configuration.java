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
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public interface Configuration {

    LookupRuleBuilder bind(Matcher<? super CallablePoint> destinationMatcher);

    InterceptionRuleBuilder intercept(Interception interception);
}
