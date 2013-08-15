package com.trioptimum.shodan.common.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 2/24/13
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParameterizedCallablePoint {

    private final CallablePoint callablePoint;

    private List<Object> parameters;

    public ParameterizedCallablePoint(CallablePoint callablePoint) {
        this.callablePoint = callablePoint;
    }

    public CallablePoint getCallablePoint() {
        return callablePoint;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public ParameterizedCallablePoint setParameters(Object... parameters) {
        this.parameters = (parameters != null) ? Arrays.asList(parameters.clone()) : Collections.EMPTY_LIST;
        return this;
    }

    public Object invoke() throws Exception {
        return callablePoint.call(parameters.toArray());
    }
}
