package com.trioptimum.shodan.helper;

import com.trioptimum.shodan.common.internal.AbstractCallablePoint;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 11.12.12
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
public class Tdcp extends AbstractCallablePoint {

    public Tdcp(Method method, TestDestination instance) {
        super(method, instance);
    }

    @Override
    public Object call(Object... params) throws Exception {
        throw new UnsupportedOperationException("use for testing purposes only");
    }
}
