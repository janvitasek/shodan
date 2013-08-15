package com.trioptimum.shodan.lookup.api;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.lookup.internal.BindingContext;
import com.trioptimum.shodan.lookup.service.LookupRule;
import com.trioptimum.shodan.matcher.api.BasicMatchers;
import com.trioptimum.shodan.matcher.internal.CallMatcherCrate;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.lang.reflect.Method;
import java.util.Comparator;

public final class LookupRuleBuilder {
	
	private final Matcher<? super CallablePoint> destinationMatcher;
	
	private Matcher<? super Key> keyMatcher = BasicMatchers.any();
	
	private Matcher<? super BindingContext> contextMatcher = BasicMatchers.any();

    private Comparator<Method> methodComparator;
	
	private LookupRuleBuilder(Matcher<? super CallablePoint> destinationMatcher) {
        if (destinationMatcher == null) {
            throw new NullPointerException("Destination matcher isn't allowed to be null.");
        }
        this.destinationMatcher = destinationMatcher;
    }
	
	public static LookupRuleBuilder bind(Matcher<? super CallablePoint> destinationMatcher) {
		return new LookupRuleBuilder(destinationMatcher);
	}
	
	public LookupRuleBuilder to(Matcher<? super Key> callMatcher) {
		this.keyMatcher = callMatcher;
		return this;
	}
	
	public LookupRuleBuilder when(Matcher<? super BindingContext> contextMatcher) {
		this.contextMatcher = contextMatcher;
		return this;
	}
	
	public LookupRuleBuilder orderBy(Comparator<Method> methodComparator) {
		this.methodComparator = methodComparator;
		return this;
	}
	
	public LookupRule done() {
		return new LookupRuleImpl(this);
	}
	
	private static class LookupRuleImpl extends CallMatcherCrate implements LookupRule {

		private final Matcher<? super CallablePoint> destinationMatcher;
		
		private final Matcher<? super BindingContext> contextMatcher;

        private final Comparator<Method> methodComparator;
		
		public LookupRuleImpl(LookupRuleBuilder ruleBuilder) {
			super(ruleBuilder.keyMatcher);
			this.destinationMatcher = ruleBuilder.destinationMatcher;
            if (ruleBuilder.contextMatcher != null) {
                this.contextMatcher = ruleBuilder.contextMatcher;
            } else {
                this.contextMatcher = BasicMatchers.any();
            }
            this.methodComparator = ruleBuilder.methodComparator;
		}
		
		public Matcher<? super CallablePoint> getDestinationMatcher() {
			return destinationMatcher;
		}
		
		public Matcher<? super BindingContext> getContextMatcher() {
			return contextMatcher;
		}

        public Comparator<Method> getMethodComparator() {
            return methodComparator;
        }
    }
}
