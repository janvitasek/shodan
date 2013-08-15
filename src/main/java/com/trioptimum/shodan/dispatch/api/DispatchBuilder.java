package com.trioptimum.shodan.dispatch.api;

import com.trioptimum.shodan.common.internal.Bindings;
import com.trioptimum.shodan.common.internal.DispatchResult;
import com.trioptimum.shodan.common.internal.Return;
import com.trioptimum.shodan.common.service.Function;
import com.trioptimum.shodan.dispatch.internal.DispatchByServices;
import com.trioptimum.shodan.dispatch.service.Dispatch;
import com.trioptimum.shodan.invocation.service.DispatchInvocation;
import com.trioptimum.shodan.lookup.api.Key;
import com.trioptimum.shodan.postproc.service.DispatchPostProcessor;

public final class DispatchBuilder {

	private Function<Key, Bindings> lookup;

	private DispatchInvocation invocation;

	private Function<DispatchResult, Return> returnExtraction;

	private DispatchPostProcessor postProcessor;

	public DispatchBuilder setLookup(Function<Key, Bindings> lookup) {
		this.lookup = lookup;
		return this;
	}

	public DispatchBuilder setInvocation(DispatchInvocation invocation) {
		this.invocation = invocation;
		return this;
	}

	public DispatchBuilder setReturnExtraction(Function<DispatchResult, Return> returnExtraction) {
		this.returnExtraction = returnExtraction;
		return this;
	}

	public DispatchBuilder setPostProcessor(DispatchPostProcessor postProcessor) {
		this.postProcessor = postProcessor;
		return this;
	}

	public Dispatch build() {
		return new DispatchByServices(lookup, invocation, returnExtraction, postProcessor);
	}
}
