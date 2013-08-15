package com.trioptimum.shodan.dispatch.internal;

import com.trioptimum.shodan.common.internal.Bindings;
import com.trioptimum.shodan.common.internal.DispatchResult;
import com.trioptimum.shodan.common.internal.ParameterizedCallablePoint;
import com.trioptimum.shodan.common.internal.Return;
import com.trioptimum.shodan.common.service.Function;
import com.trioptimum.shodan.dispatch.service.DispatchOrchestration;
import com.trioptimum.shodan.extraction.internal.SimpleReturnExtraction;
import com.trioptimum.shodan.invocation.api.InterceptableDispatchInvocation;
import com.trioptimum.shodan.invocation.service.DispatchInvocation;
import com.trioptimum.shodan.lookup.api.Key;
import com.trioptimum.shodan.lookup.internal.NullLookup;
import com.trioptimum.shodan.postproc.internal.NullDispatchPostProcessor;
import com.trioptimum.shodan.postproc.service.DispatchPostProcessor;

import java.util.Collection;

public final class DispatchByServices extends DispatchOrchestration {

    private final Function<Key, Bindings> lookup;

    private final DispatchInvocation invocation;

    private final Function<DispatchResult, Return> returnExtraction;

    private final DispatchPostProcessor postProcessor;

    public DispatchByServices(Function<Key, Bindings> lookup, DispatchInvocation invocation,
                              Function<DispatchResult, Return> returnExtraction, DispatchPostProcessor postProcessor) {
        this.lookup = 		    ((lookup != null) ? lookup : new NullLookup());
        this.invocation =       ((invocation != null) ? invocation : new InterceptableDispatchInvocation());
        this.returnExtraction = ((returnExtraction != null) ? returnExtraction : new SimpleReturnExtraction());
        this.postProcessor =    ((postProcessor != null) ? postProcessor : new NullDispatchPostProcessor());
    }

    public Bindings find(Key key) {
        return lookup.apply(key);
    }

    @Override
    public DispatchResult invokeAll(Collection<? extends ParameterizedCallablePoint> bindings) {
        return invocation.invokeAll(bindings);
    }

    @Override
    public Return extractReturn(DispatchResult dispatchResult) {
        return returnExtraction.apply(dispatchResult);
    }

    @Override
    public void postProcess(DispatchResult dispatchResult, Return dispatchReturn) {
        postProcessor.postProcessDispatch(dispatchResult, dispatchReturn);
    }
}
