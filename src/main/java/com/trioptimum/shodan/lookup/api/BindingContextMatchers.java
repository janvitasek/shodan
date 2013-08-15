package com.trioptimum.shodan.lookup.api;

import com.trioptimum.shodan.common.internal.Binding;
import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.lookup.internal.BindingContext;
import com.trioptimum.shodan.lookup.service.LookupRule;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.util.List;

import static com.trioptimum.shodan.matcher.api.CollectionMatchers.isEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 2.12.12
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public class BindingContextMatchers {

    public static Matcher<BindingContext> binding(Matcher<? super Binding> bindingMatcher) {
        return new BindingMatcher(bindingMatcher);
    }

    public static Matcher<BindingContext> noPrevRuleMatched() {
        return new PrevRulesMatcher(isEmpty());
    }

    public static Matcher<BindingContext> prevRulesMatched(Matcher<? super List<? super LookupRule>> lookupRulesMatcher) {
        return new PrevRulesMatcher(lookupRulesMatcher);
    }

    public static Matcher<BindingContext> prevBindings(Matcher<? super List<? super CallablePoint>> bindingsMatcher) {
        return new PrevBindingsMatcher(bindingsMatcher);
    }

    private static class BindingMatcher implements Matcher<BindingContext> {

        final Matcher<? super Binding> bindingMatcher;

        BindingMatcher(Matcher<? super Binding> bindingMatcher) {
            this.bindingMatcher = bindingMatcher;
        }

        public boolean matches(BindingContext tested) {
            return bindingMatcher.matches(tested.getBinding());
        }
    }

    private static class PrevRulesMatcher implements Matcher<BindingContext> {

        final Matcher<? super List<? super LookupRule>> lookupRulesMatcher;

        PrevRulesMatcher(Matcher<? super List<? super LookupRule>> lookupRulesMatcher) {
            this.lookupRulesMatcher = lookupRulesMatcher;
        }
        public boolean matches(BindingContext tested) {
            return lookupRulesMatcher.matches(tested.getPreviouslyMatchedRules());
        }

    }

    private static class PrevBindingsMatcher implements Matcher<BindingContext> {

        final Matcher<? super List<? super CallablePoint>> bindingsMatcher;

        PrevBindingsMatcher(Matcher<? super List<? super CallablePoint>> bindingsMatcher) {
            this.bindingsMatcher = bindingsMatcher;
        }
        public boolean matches(BindingContext tested) {
            return bindingsMatcher.matches(tested.getPreviousBindings());
        }

    }
}
