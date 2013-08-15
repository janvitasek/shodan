package com.trioptimum.shodan.invocation.service;

import com.trioptimum.shodan.common.internal.DispatchResult;
import com.trioptimum.shodan.common.internal.ParameterizedCallablePoint;

import java.util.Collection;

public interface DispatchInvocation {

	DispatchResult invokeAll(Collection<? extends ParameterizedCallablePoint> bindings);
}
