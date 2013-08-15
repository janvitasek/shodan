package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.lang.reflect.Method;

public class CallablePointMatchers {
	
	public static Matcher<CallablePoint> method(final Matcher<? super Method> matcher) {
		return new MethodMatcher(matcher);
	}

    private static class MethodMatcher implements Matcher<CallablePoint> {

        final Matcher<? super Method> matcher;

        MethodMatcher(Matcher<? super Method> matcher) {
            this.matcher = matcher;
        }

        public boolean matches(CallablePoint tested) {
            return matcher.matches(tested.getMethod());
        }
    }

	public static Matcher<CallablePoint> instance(final Matcher<? super Object> matcher) {
		return new InstanceMatcher(matcher);
	}

    private static class InstanceMatcher implements Matcher<CallablePoint> {

        final Matcher<? super Object> matcher;

        InstanceMatcher(Matcher<? super Object> matcher) {
            this.matcher = matcher;
        }

        public boolean matches(CallablePoint tested) {
            return matcher.matches(tested.getInstance());
        }
    }
}
