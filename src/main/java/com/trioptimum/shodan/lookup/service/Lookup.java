package com.trioptimum.shodan.lookup.service;

import com.trioptimum.shodan.common.internal.Bindings;
import com.trioptimum.shodan.common.service.Function;
import com.trioptimum.shodan.lookup.api.Key;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 2.12.12
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public interface Lookup extends Function<Key, Bindings> {
}
