package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.matcher.service.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 18.12.12
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class NumberMatcher {

    public static Matcher<Number> sameOrGreaterThan(int number) {
        return new SameOrGreaterThan(number);
    }

    private static class SameOrGreaterThan implements Matcher<Number> {

        final int number;

        SameOrGreaterThan(int number) {
            this.number = number;
        }

        public boolean matches(Number tested) {
            return (tested.longValue() >= number);
        }
    }
}
