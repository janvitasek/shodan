package com.trioptimum.shodan.intercept.internal;

import com.trioptimum.shodan.common.internal.ParameterizedCallablePoint;

public final class CallingDispatchContext extends DispatchContext {

	public CallingDispatchContext(ParameterizedCallablePoint binding) {
		super(binding);
	}

	public Object call() throws Exception {
        return binding.invoke();
    }
}
