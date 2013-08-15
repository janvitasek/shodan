package com.trioptimum.shodan.delegate.api;

import com.trioptimum.shodan.common.internal.CallMetadata;
import com.trioptimum.shodan.delegate.internal.InvocationToDispatch;
import com.trioptimum.shodan.delegate.service.DelegateFactory;
import com.trioptimum.shodan.dispatch.service.Dispatch;

import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 25.11.12
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
public class DispatchDelegateFactory implements DelegateFactory {

    private final Dispatch dispatch;

    public DispatchDelegateFactory(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    @SuppressWarnings("unchecked")
    public <D> D createDelegate(Class<D> delegateClass, CallMetadata metadata) {
        return (D) Proxy.newProxyInstance(delegateClass.getClassLoader(), new Class[] {delegateClass}, new InvocationToDispatch(dispatch, metadata));
    }
}
