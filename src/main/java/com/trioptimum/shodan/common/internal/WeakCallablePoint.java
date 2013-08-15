package com.trioptimum.shodan.common.internal;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public class WeakCallablePoint extends WeakReference<Object> implements CallablePoint {

	protected final Method method;

	protected final int hash;

	public WeakCallablePoint(Method method, Object instance, ReferenceQueue<Object> queue) {
		super(instance, queue);
		this.method = method;
		this.hash = Util.hash(method, instance);
	}
	
	public Method getMethod() {
		return method;
	}

	public Object getInstance() {
		return super.get();
	}

	public Object call(Object... params) throws Exception {
		Object instance = getInstance();
		if (instance == null) {
			 throw new IllegalStateException("Instance unreachable");
		}

		return callSafely(instance, params);
	}
	
	protected Object callSafely(Object instance, Object... params) throws Exception {
		throw new UnsupportedOperationException("Call operation is unsupported");
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof CallablePoint) {
			CallablePoint other = (CallablePoint) obj;
			return getInstance() == other.getInstance()
				   && Util.equals(method, other.getMethod());
		}
		
		return false;
	}
}
