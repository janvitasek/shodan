package com.trioptimum.shodan.facade.internal;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.facade.service.Configuration;
import com.trioptimum.shodan.intercept.api.InterceptionRuleBuilder;
import com.trioptimum.shodan.intercept.service.Interception;
import com.trioptimum.shodan.intercept.service.InterceptionRule;
import com.trioptimum.shodan.lookup.api.LookupRuleBuilder;
import com.trioptimum.shodan.lookup.service.LookupRule;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 1.12.12
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class DefaultConfiguration implements Configuration {

    private final List<LookupRuleBuilder> lookupRuleBuilders;

    private final List<InterceptionRuleBuilder> interceptionRuleBuilders;

    public DefaultConfiguration() {
        this.lookupRuleBuilders = new ArrayList<LookupRuleBuilder>();
        this.interceptionRuleBuilders = new ArrayList<InterceptionRuleBuilder>();
    }

    public LookupRuleBuilder bind(Matcher<? super CallablePoint> destinationMatcher) {
        LookupRuleBuilder lrb = LookupRuleBuilder.bind(destinationMatcher);
        lookupRuleBuilders.add(lrb);
        return lrb;
    }

    public InterceptionRuleBuilder intercept(Interception interception) {
        InterceptionRuleBuilder irb = InterceptionRuleBuilder.intercept(interception);
        interceptionRuleBuilders.add(irb);
        return irb;
    }

    public List<LookupRule> getLookupRules() {
        List<LookupRule> retval = new ArrayList<LookupRule>(lookupRuleBuilders.size());
        for (LookupRuleBuilder lrb : lookupRuleBuilders) {
            retval.add(lrb.done());
        }

        return retval;
    }

    public List<InterceptionRule> getInterceptionRules() {
        List<InterceptionRule> retval = new ArrayList<InterceptionRule>(interceptionRuleBuilders.size());
        for (InterceptionRuleBuilder irb : interceptionRuleBuilders) {
            retval.add(irb.done());
        }

        return retval;
    }
}
