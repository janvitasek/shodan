package com.trioptimum.shodan.intercept.api;

import com.trioptimum.shodan.intercept.internal.DispatchContext;
import com.trioptimum.shodan.intercept.internal.InterceptedDispatchContext;
import com.trioptimum.shodan.intercept.service.Interception;
import com.trioptimum.shodan.intercept.service.InterceptionRule;

import java.util.*;

public final class InterceptionByRule implements Interception {

	private final List<InterceptionRule> interceptionRules;

    public InterceptionByRule(InterceptionRule... rules) {
        this(Arrays.asList(rules));
    }

    public InterceptionByRule(Collection<? extends InterceptionRule> rules) {
        this.interceptionRules = new ArrayList<InterceptionRule>(rules);
    }

	public Object intercept(DispatchContext context) throws Exception {
		DispatchContext current = context;
		for (ListIterator<InterceptionRule> it = interceptionRules.listIterator(interceptionRules.size()); it.hasPrevious();) {
			InterceptionRule rule = it.previous();
			if (rule.getCallDestinationMatcher().matches(context.getBinding().getCallablePoint())) {
				current = new InterceptedDispatchContext(rule.getInterception(), current);
			}
		}
		
		return current.call();
	}
}
