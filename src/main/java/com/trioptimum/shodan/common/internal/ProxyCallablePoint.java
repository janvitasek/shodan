package com.trioptimum.shodan.common.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 25.11.12
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
public final class ProxyCallablePoint extends AbstractCallablePoint {

    private final InvocationHandler invocationHandler;

    public ProxyCallablePoint(Method method, Object instance, InvocationHandler invocationHandler) {
        super(method, instance);
        this.invocationHandler = invocationHandler;
    }

    @Override
    public Object call(Object... params) throws Exception {
        try {
            return invocationHandler.invoke(instance, method, params);
        } catch (Throwable t) {
            throw Util.launderThrowable(t);
        }
    }
}
