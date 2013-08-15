package com.trioptimum.shodan.lookup;

import com.trioptimum.shodan.common.internal.*;
import com.trioptimum.shodan.facade.DelegationTest;
import com.trioptimum.shodan.helper.*;
import com.trioptimum.shodan.lookup.api.Key;
import com.trioptimum.shodan.lookup.api.LookupByRules;
import com.trioptimum.shodan.lookup.service.LookupRule;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.trioptimum.shodan.lookup.api.BindingContextMatchers.binding;
import static com.trioptimum.shodan.lookup.api.LookupRuleBuilder.bind;
import static com.trioptimum.shodan.matcher.api.BasicMatchers.none;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 12.12.12
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public class LookupByRulesTest {

    LookupByRules sut;

    TestDestination destination = new TestDestination();

    final Key call = new Key(new Call(new CallablePointUsingReflection(TestDelegateMethods.integerArgVoidMethod(), new TestDelegateImpl()),
                                      new Object[]{DelegationTest.PARAM},
                                      new CallMetadataBuilder().setSource(TestSource.class).setMarkers(DelegationTest.TEST_ANNOTATION).build()));

    void setup(LookupRule lookupRule) {
        sut = new LookupByRules(lookupRule);
        sut.add(destination);
        sut.add(this);
    }

    static CallablePoint getBinding(Bindings bindings) {
        return bindings.getBindings().get(0);
    }

    void assertBinding(Bindings bs) {
        assertThat(bs.getBindings()).hasSize(1);
        assertThat(bs.getKey()).isEqualTo(call);
        assertThat(getBinding(bs).getInstance()).isEqualTo(destination);
        assertThat(getBinding(bs).getMethod()).isEqualTo(TestDestination.integerArgVoidMethod());
    }

    static void assertNoBinding(Bindings bs) {
        assertThat(bs.getBindings()).isEmpty();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void nullRules() {
        new LookupByRules((LookupRule) null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void emptyRules() {
        new LookupByRules(Collections.EMPTY_SET);
    }

    @Test
    public void destinationMatcher() {
        setup(bind(TestMatchers.integerArgVoidMatcher(destination)).done());

        Bindings bs = sut.apply(call);

        assertBinding(bs);
    }

    @Test
    public void destinationMatcherFalse() {
        setup(bind(none()).done());

        Bindings bs = sut.apply(call);

        assertNoBinding(bs);
    }

    @Test
    public void callMatcher() {
        setup(bind(TestMatchers.integerArgVoidMatcher(destination)).to(TestMatchers.integerArgVoidCallMatcher()).done());

        Bindings bs = sut.apply(call);

        assertBinding(bs);
    }

    @Test
    public void callMatcherFalse() {
        setup(bind(TestMatchers.integerArgVoidMatcher(destination)).to(none()).done());

        Bindings bs = sut.apply(call);

        assertNoBinding(bs);
    }

    @Test
    public void bindingMatcher() {
        setup(bind(TestMatchers.integerArgVoidMatcher(destination)).when(binding(TestMatchers.integerArgVoidBindingMatcher(destination))).done());

        Bindings bs = sut.apply(call);

        assertBinding(bs);
    }

    @Test
    public void bindingMatcherFalse() {
        setup(bind(TestMatchers.integerArgVoidMatcher(destination)).when(binding(none())).done());

        Bindings bs = sut.apply(call);

        assertNoBinding(bs);
    }
}
