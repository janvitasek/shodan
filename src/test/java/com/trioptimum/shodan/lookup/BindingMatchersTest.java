package com.trioptimum.shodan.lookup;

import org.testng.annotations.Test;

import static com.trioptimum.shodan.helper.TestBindings.from;
import static com.trioptimum.shodan.helper.TestDestination.*;
import static com.trioptimum.shodan.lookup.api.BindingMatchers.compatibleParams;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 11.12.12
 * Time: 19:59
 * To change this template use File | Settings | File Templates.
 */
public class BindingMatchersTest {

    @Test
    public void compatibleParamsPositive() {
        assertTrue(compatibleParams().matches(from(twoArgsVoidMethod()).to(twoArgsStringMethod())));
    }

    @Test
    public void compatibleParamsDiffCountPositive() {
        assertTrue(compatibleParams().matches(from(fourArgsVoidMethod()).to(twoArgsStringMethod())));
    }

    @Test
    public void compatibleParamsNegative() {
        assertFalse(compatibleParams().matches(from(twoArgsVoidMethod()).to(noArgVoidMethod())));
    }
}
