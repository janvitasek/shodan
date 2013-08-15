package com.trioptimum.shodan.common.internal;

import java.util.List;

public final class DispatchResult {

	private final List<Return> returns;

	public DispatchResult(List<Return> returns) {
		this.returns = returns;
	}

	public List<Return> getReturns() {
		return returns;
	}
}
