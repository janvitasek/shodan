package com.trioptimum.shodan.matcher.internal;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.matcher.service.Matcher;

public interface CallDestinationCheckRule {

	Matcher<? super CallablePoint> getCallDestinationMatcher();
}
