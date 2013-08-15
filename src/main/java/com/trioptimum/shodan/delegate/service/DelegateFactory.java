package com.trioptimum.shodan.delegate.service;

import com.trioptimum.shodan.common.internal.CallMetadata;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 25.11.12
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public interface DelegateFactory {

    <D> D createDelegate(Class<D> delegateClass, CallMetadata metadata);
}
