package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.matcher.service.Matcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 1/17/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MatchersList {

    public static <T> List<Matcher<T>> item(Matcher<T> m1) {
        return Collections.singletonList(m1);
    }

    public static <T> List<Matcher<? super T>> these(Matcher<? super T> m1, Matcher<? super T> m2) {
        return construct(m1, m2);
    }

    public static <T> List<Matcher<? super T>> these(Matcher<? super T> m1, Matcher<? super T> m2, Matcher<? super T> m3) {
        return construct(m1, m2, m3);
    }


    public static <T> List<Matcher<? super T>> these(Matcher<? super T> m1, Matcher<? super T> m2, Matcher<? super T> m3, Matcher<? super T> m4) {
        return construct(m1, m2, m3, m4);
    }

    public static <T> List<Matcher<? super T>> these(Matcher<? super T>... matchers) {
        return construct(matchers);
    }

    private static <T> List<Matcher<? super T>> construct(Matcher<? super T>... matchers) {
        return Arrays.asList(matchers);
    }
}
