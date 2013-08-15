package com.trioptimum.shodan.extraction.internal;

import com.trioptimum.shodan.common.internal.DispatchResult;
import com.trioptimum.shodan.common.internal.Return;
import com.trioptimum.shodan.extraction.service.ReturnExtraction;

public final class SimpleReturnExtraction implements ReturnExtraction {

	public Return apply(DispatchResult dispatchResult) {
        Object ret = dispatchResult.getReturns().size() == 0 ? null : dispatchResult.getReturns().get(0).getValue();
		return new Return(ret);
	}
}
