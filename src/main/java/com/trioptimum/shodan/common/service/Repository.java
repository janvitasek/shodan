package com.trioptimum.shodan.common.service;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 24.11.12
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public interface Repository<T> {

    T add(Object object);

    T addWeakly(Object object);

    T addAll(Collection<?> objects);

    T addAllWeakly(Collection<?> objects);

    T remove(Object object);

    T removeAll(Collection<?> objects);
}
