package com.trioptimum.shodan.postproc.service;

import com.trioptimum.shodan.common.internal.DispatchResult;
import com.trioptimum.shodan.common.internal.Return;

public interface DispatchPostProcessor {

	void postProcessDispatch(DispatchResult dispatchResult, Return dispatchReturn);
}
