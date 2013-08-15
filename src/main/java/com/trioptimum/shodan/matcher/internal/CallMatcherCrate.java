package com.trioptimum.shodan.matcher.internal;

import com.trioptimum.shodan.lookup.api.Key;
import com.trioptimum.shodan.matcher.service.Matcher;

public class CallMatcherCrate implements CallCheckRule {

	protected final Matcher<? super Key> invocationMatcher;
	
	public CallMatcherCrate(Matcher<? super Key> invocationMatcher) {
		this.invocationMatcher = invocationMatcher;
	}
	
	public Matcher<? super Key> getKeyMatcher() {
		return invocationMatcher;
	}
}
