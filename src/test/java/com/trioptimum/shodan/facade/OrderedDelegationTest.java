package com.trioptimum.shodan.facade;

import com.trioptimum.shodan.facade.service.AbstractSettings;
import com.trioptimum.shodan.helper.PriorityComparator;
import com.trioptimum.shodan.helper.TestDestination;
import com.trioptimum.shodan.intercept.api.NullForMissingParams;
import org.testng.annotations.Test;

import static com.trioptimum.shodan.helper.TestDestination.*;
import static com.trioptimum.shodan.matcher.api.BasicMatchers.*;
import static com.trioptimum.shodan.matcher.api.CallablePointMatchers.method;
import static com.trioptimum.shodan.matcher.api.ReflectMatchers.declaredInObject;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 2.12.12
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
public class OrderedDelegationTest extends DelegationTest {

    @Test
    public void orderingTest() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(method(all(any(), not(declaredInObject())))).orderBy(new PriorityComparator());
                intercept(new NullForMissingParams());
            }
        });

        testDelegate.noArgVoid();

        assertThat(destination.calls).hasSize(TestDestination.METHOD_COUNT);
        assertThat(destination.calls).startsWith(twoSwappedArgsVoidMethod(), twoArgsStringMethod(), noArgVoidMethod() ,twoArgsVoidMethod());
    }
}
