package com.trioptimum.shodan.common.internal;

import java.lang.reflect.Method;

public interface CallablePoint {

	Object call(Object... params) throws Exception;

	Method getMethod();

	Object getInstance();
}