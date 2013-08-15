package com.trioptimum.shodan.helper;

import com.trioptimum.shodan.common.internal.Binding;
import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.facade.DelegationTest;
import com.trioptimum.shodan.lookup.api.Key;
import com.trioptimum.shodan.matcher.service.Matcher;

import static com.trioptimum.shodan.lookup.api.BindingMatchers.destination;
import static com.trioptimum.shodan.lookup.api.BindingMatchers.key;
import static com.trioptimum.shodan.matcher.api.BasicMatchers.*;
import static com.trioptimum.shodan.matcher.api.CallMatchers.*;
import static com.trioptimum.shodan.matcher.api.CallablePointMatchers.instance;
import static com.trioptimum.shodan.matcher.api.CallablePointMatchers.method;
import static com.trioptimum.shodan.matcher.api.IterableMatchers.contains;
import static com.trioptimum.shodan.matcher.api.KeyMatchers.call;
import static com.trioptimum.shodan.matcher.api.MatchersList.item;
import static com.trioptimum.shodan.matcher.api.ReflectMatchers.iface;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 17.12.12
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class TestMatchers {

    public static Matcher<CallablePoint> noArgVoidMatcher(TestDestination instance) {
        return all(method(is(TestDestination.noArgVoidMethod())), instance(is(instance)));
    }

    public static Matcher<CallablePoint> integerArgVoidMatcher(TestDestination instance) {
        return all(method(is(TestDestination.integerArgVoidMethod())), instance(is(instance)));
    }

    public static Matcher<Key> integerArgVoidCallMatcher() {
        return call(all(source(is(TestSource.class)),
                        target(all(method(is(TestDelegateMethods.integerArgVoidMethod())),
                                   instance(type(iface(is(TestDelegate.class)))))),
                        params(contains(item(is(DelegationTest.PARAM)))),
                        markers(contains(item(is(DelegationTest.TEST_ANNOTATION))))));
    }

    public static Matcher<Binding> integerArgVoidBindingMatcher(TestDestination destination) {
        return all(key(integerArgVoidCallMatcher()), destination(integerArgVoidMatcher(destination)));
    }
}
