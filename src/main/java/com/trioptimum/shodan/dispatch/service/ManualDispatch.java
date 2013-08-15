package com.trioptimum.shodan.dispatch.service;

import com.trioptimum.shodan.common.internal.Bindings;
import com.trioptimum.shodan.common.internal.ParameterizedCallablePoint;
import com.trioptimum.shodan.lookup.api.Key;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 2/24/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ManualDispatch extends Dispatch {

    Bindings find(Key key);

    Object dispatchFound(Collection<? extends ParameterizedCallablePoint> bindings) throws Exception;
}
