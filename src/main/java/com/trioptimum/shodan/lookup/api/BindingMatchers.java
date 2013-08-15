package com.trioptimum.shodan.lookup.api;

import com.trioptimum.shodan.common.internal.Binding;
import com.trioptimum.shodan.common.internal.Call;
import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.matcher.service.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 9.12.12
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
public class BindingMatchers {

    public static Matcher<Binding> key(Matcher<Key> callMatcher) {
        return new CallMatcher(callMatcher);
    }

    public static Matcher<Binding> destination(Matcher<CallablePoint> destinationMatcher) {
        return new DestinationMatcher(destinationMatcher);
    }

    public static Matcher<Binding> compatibleParams() {
        return new CompatibleParamsMatcher();
    }

    public static class CallMatcher implements Matcher<Binding> {

        final Matcher<Key> callMatcher;

        public CallMatcher(Matcher<Key> callMatcher) {
            this.callMatcher = callMatcher;
        }

        public boolean matches(Binding tested) {
            return callMatcher.matches(tested.getKey());
        }
    }

    public static class DestinationMatcher implements Matcher<Binding> {

        final Matcher<CallablePoint> destinationMatcher;

        public DestinationMatcher(Matcher<CallablePoint> destinationMatcher) {
            this.destinationMatcher = destinationMatcher;
        }

        public boolean matches(Binding tested) {
            return destinationMatcher.matches(tested.getDestination());
        }
    }

    public static class CompatibleParamsMatcher implements Matcher<Binding> {

        public boolean matches(Binding tested) {
            Class<?>[] destParamTypes = tested.getDestination().getMethod().getParameterTypes();
            if (destParamTypes.length == 0) {
                return false;
            }
            Call inv = tested.getKey().get(Call.class);
            if (inv == null) {
                return false;
            }
            Class<?>[] callParamTypes = inv.getTarget().getMethod().getParameterTypes();
            int lastPos = 0;
            outer: for (Class<?> dpt : destParamTypes) {
                while (lastPos < callParamTypes.length) {
                    if (dpt.isAssignableFrom(callParamTypes[lastPos++])) {
                        continue outer;
                    }
                }
                return false;
            }

            return true;
        }
    }
}
