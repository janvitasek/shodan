package com.trioptimum.shodan.lookup.service;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.lookup.internal.BindingContext;
import com.trioptimum.shodan.matcher.internal.CallCheckRule;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.lang.reflect.Method;
import java.util.Comparator;

public interface LookupRule extends CallCheckRule {

	Matcher<? super CallablePoint> getDestinationMatcher();

    Matcher<? super BindingContext> getContextMatcher();

    Comparator<Method> getMethodComparator();
}
