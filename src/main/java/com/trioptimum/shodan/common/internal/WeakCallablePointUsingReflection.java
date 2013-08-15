package com.trioptimum.shodan.common.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;

public class WeakCallablePointUsingReflection extends WeakCallablePoint {

	public WeakCallablePointUsingReflection(Method method, Object instance, ReferenceQueue<Object> queue) {
		super(method, instance, queue);
	}

	@Override
	protected Object callSafely(Object instance, Object... params) throws Exception {
		return new CallablePointUsingReflection(method, instance).call(params);
	}
	
	public CallablePoint getStrong() {
		Object instance = getInstance();
		return (instance != null) ? new CallablePointUsingReflection(method, instance) : null;
	}
}
