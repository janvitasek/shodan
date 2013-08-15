package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.common.internal.Call;
import com.trioptimum.shodan.lookup.api.Key;
import com.trioptimum.shodan.matcher.service.Matcher;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 2/17/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeyMatchers {

    public static Matcher<Key> call(Matcher<? super Call> callMatcher) {
        return new CallMatcher(callMatcher);
    }

    private static class CallMatcher implements Matcher<Key> {

        final Matcher<? super Call> callMatcher;

        CallMatcher(Matcher<? super Call> callMatcher) {
            this.callMatcher = callMatcher;
        }

        public boolean matches(Key tested) {
            Call call = tested.get(Call.class);
            if (call == null) {
                return false;
            }
            return callMatcher.matches(call);
        }
    }

}
