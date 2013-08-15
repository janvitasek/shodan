package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.matcher.service.Matcher;

import java.util.Collection;

import static com.trioptimum.shodan.matcher.api.BasicMatchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 2.12.12
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public class CollectionMatchers {

    public static Matcher<Collection> size(Matcher<? super Integer> sizeMatcher) {
        return new SizeMatcher(sizeMatcher);
    }

    public static Matcher<Collection> isEmpty() {
        return new SizeMatcher(is(0));
    }

    private static class SizeMatcher implements Matcher<Collection> {

        final Matcher<? super Integer> sizeMatcher;

        SizeMatcher(Matcher<? super Integer> sizeMatcher) {
            this.sizeMatcher = sizeMatcher;
        }

        public boolean matches(Collection tested) {
            return sizeMatcher.matches(tested.size());
        }
    }

}
