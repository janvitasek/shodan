package com.trioptimum.shodan.intercept.api;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.intercept.service.Interception;
import com.trioptimum.shodan.intercept.service.InterceptionRule;
import com.trioptimum.shodan.matcher.api.BasicMatchers;
import com.trioptimum.shodan.matcher.service.Matcher;

public final class InterceptionRuleBuilder {

	private final Interception interception;
	
	private Matcher<? super CallablePoint> callDestinationMatcher = BasicMatchers.any();
	
	private InterceptionRuleBuilder(Interception interception) {
		this.interception = interception;
	}
	
	public static InterceptionRuleBuilder intercept(Interception interception) {
		return new InterceptionRuleBuilder(interception);
	}
	
	public InterceptionRuleBuilder when(Matcher<CallablePoint> callDestinationMatcher) {
		this.callDestinationMatcher = callDestinationMatcher;
		return this;
	}
	
	public InterceptionRule done() {
		return new InterceptionRuleImpl(this);
	}
	
	private static class InterceptionRuleImpl implements InterceptionRule {

		private final Interception interception;
		
		private final Matcher<? super CallablePoint> callDestinationMatcher;

		public InterceptionRuleImpl(InterceptionRuleBuilder ruleBuilder) {
			this.interception = ruleBuilder.interception;
			this.callDestinationMatcher = ruleBuilder.callDestinationMatcher;
		}

		public Interception getInterception() {
			return interception;
		}
		
		public Matcher<? super CallablePoint> getCallDestinationMatcher() {
			return callDestinationMatcher;
		}
	}
}
