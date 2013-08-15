package com.trioptimum.shodan.lookup.api;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 2/16/13
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Key {

    private final Map<Class<?>, Object> entries = new HashMap<Class<?>, Object>();

    public Key(Object object) {
        this(Collections.singleton(object));
    }

    public Key(Collection<?> objects) {
        for (Object o : objects) {
            entries.put(o.getClass(), o);
        }
    }

    public <T> T get(Class<T> type) {
        return type.cast(entries.get(type));
    }
}
