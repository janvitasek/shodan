package com.trioptimum.shodan.common.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CallablePointUsingReflection extends AbstractCallablePoint {

	public CallablePointUsingReflection(Method method, Object instance) {
		super(method, instance);
	}

	@Override
	public Object call(Object... params) throws Exception {
		try {
			return method.invoke(instance, params);
		} catch (InvocationTargetException e) {
            throw Util.launderThrowable(e.getCause());
		}
	}
}
