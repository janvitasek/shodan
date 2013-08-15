package com.trioptimum.shodan.postproc.internal;

import com.trioptimum.shodan.common.internal.DispatchResult;
import com.trioptimum.shodan.common.internal.Return;
import com.trioptimum.shodan.postproc.service.DispatchPostProcessor;

public final class NullDispatchPostProcessor implements DispatchPostProcessor {

	public void postProcessDispatch(DispatchResult dispatchResult, Return dispatchReturn) {	/* does nothing */ }
}
