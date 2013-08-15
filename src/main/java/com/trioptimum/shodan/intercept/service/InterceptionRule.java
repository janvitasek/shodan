package com.trioptimum.shodan.intercept.service;

import com.trioptimum.shodan.matcher.internal.CallDestinationCheckRule;

public interface InterceptionRule extends CallDestinationCheckRule {

	Interception getInterception();
}
