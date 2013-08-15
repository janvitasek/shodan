package com.trioptimum.shodan.delegate.internal;

import com.trioptimum.shodan.common.internal.Call;
import com.trioptimum.shodan.common.internal.CallMetadata;
import com.trioptimum.shodan.common.internal.ProxyCallablePoint;
import com.trioptimum.shodan.dispatch.service.Dispatch;
import com.trioptimum.shodan.lookup.api.Key;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 25.11.12
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class InvocationToDispatch implements InvocationHandler {

    private final Dispatch dispatch;

    private final CallMetadata metadata;

    public InvocationToDispatch(Dispatch dispatch, CallMetadata metadata) {
        this.dispatch = dispatch;
        this.metadata = metadata;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if("equals".equals(name)) {
                return proxy == args[0];
            } else if("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if("toString".equals(name)) {
                return proxy.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(proxy)) + " Delegate";
            } else {
                throw new UnsupportedOperationException("Unsupported method: " + method.toString());
            }
        }

        return dispatch.dispatch(new Key(new Call(new ProxyCallablePoint(method, proxy, this), args, metadata)), args);
    }
}
