package com.trioptimum.shodan.helper;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
* Created with IntelliJ IDEA.
* User: Turtles
* Date: 12.12.12
* Time: 16:50
* To change this template use File | Settings | File Templates.
*/
public class PriorityComparator implements Comparator<Method> {

    public int compare(Method m1, Method m2) {
        Priority p1 = m1.getAnnotation(Priority.class);
        Priority p2 = m2.getAnnotation(Priority.class);
        if (p1 == null) {
            return p2 == null ? 0 : 1;
        }
        if (p2 == null) {
            return -1;
        }
        return p2.value() - p1.value();
    }
}
