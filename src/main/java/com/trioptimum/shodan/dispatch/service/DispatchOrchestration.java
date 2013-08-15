package com.trioptimum.shodan.dispatch.service;

import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.common.internal.DispatchResult;
import com.trioptimum.shodan.common.internal.ParameterizedCallablePoint;
import com.trioptimum.shodan.common.internal.Return;
import com.trioptimum.shodan.lookup.api.Key;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DispatchOrchestration implements ManualDispatch {

    protected abstract DispatchResult invokeAll(Collection<? extends ParameterizedCallablePoint> bindings);
	
	protected abstract Return extractReturn(DispatchResult dispatchResult);
	
	protected void postProcess(DispatchResult dispatchResult, Return dispatchReturn) { /* empty */ }
	
	protected Object doReturn(Return ret) throws Exception {
		if (ret.isExceptional()) {
			throw ret.getException();
		} else {
			return ret.getValue();
		}
	}
	
	public Object dispatch(Key key, Object... params) throws Exception {
        List<CallablePoint> cps = find(key).getBindings();
        List<ParameterizedCallablePoint> pcps = new ArrayList<ParameterizedCallablePoint>(cps.size());
        for (CallablePoint cp : cps) {
            pcps.add(new ParameterizedCallablePoint(cp).setParameters(params));
        }

        return dispatchFound(pcps);
    }

    public Object dispatchFound(Collection<? extends ParameterizedCallablePoint> bindings) throws Exception {
        DispatchResult res = invokeAll(bindings);
        Return ret = extractReturn(res);
        postProcess(res, ret);
        return doReturn(ret);
    }
}
