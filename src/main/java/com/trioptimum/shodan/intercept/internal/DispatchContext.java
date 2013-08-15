package com.trioptimum.shodan.intercept.internal;

import com.trioptimum.shodan.common.internal.ParameterizedCallablePoint;

import java.util.concurrent.Callable;

public abstract class DispatchContext implements Callable<Object> {
	
	protected final ParameterizedCallablePoint binding;

	protected boolean lastDispatch;

	public DispatchContext(ParameterizedCallablePoint binding) {
		this.binding = binding;
	}

	public ParameterizedCallablePoint getBinding() {
		return binding;
	}

	public boolean isLastDispatch() {
		return lastDispatch;
	}

	public void setLastDispatch(boolean lastDispatch) {
		this.lastDispatch = lastDispatch;
	}
}
