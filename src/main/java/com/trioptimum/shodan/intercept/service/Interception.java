package com.trioptimum.shodan.intercept.service;

import com.trioptimum.shodan.intercept.internal.DispatchContext;

public interface Interception {

	Object intercept(DispatchContext context) throws Exception;
}
