package com.trioptimum.shodan.facade;

import com.trioptimum.shodan.common.internal.CallMetadataBuilder;
import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.facade.api.Shodan;
import com.trioptimum.shodan.facade.service.Delegation;
import com.trioptimum.shodan.facade.service.Settings;
import com.trioptimum.shodan.helper.*;
import com.trioptimum.shodan.matcher.service.Matcher;
import org.testng.annotations.BeforeMethod;

import java.lang.annotation.Annotation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 2.12.12
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
public class DelegationTest {

    public static final Integer PARAM = 69;

    public static final TestAnnotation TEST_ANNOTATION = new TestAnnotation() {

        public Class<? extends Annotation> annotationType() {
            return null;
        }
    };

    protected TestDelegate testDelegate;

    protected TestDestination destination;

    @BeforeMethod
    public void setup() {
        destination = new TestDestination();
    }

    public void setup(Settings settings) {
        Delegation dlg = new Shodan(settings);
        dlg.add(destination);
        testDelegate = dlg.createDelegate(TestDelegate.class, new CallMetadataBuilder().setSource(TestSource.class).setMarkers(TEST_ANNOTATION).build());
    }

    protected void callAndAssertInvoked() {
        callAndAssertInvoked(1);
    }

    protected void callAndAssertInvoked(int invCount) {
        testDelegate.integerArgVoid(PARAM);
        assertThat(destination.calls).hasSize(invCount);
        assertTrue(TestDestination.integerArgVoidMethod().equals(destination.calls.getFirst()));
    }

    protected void callAndAssertNoInvocation() {
        testDelegate.noArgVoid();
        assertThat(destination.calls).isEmpty();
    }

    protected Matcher<CallablePoint> integerArgVoidMatcher() {
        return TestMatchers.integerArgVoidMatcher(destination);
    }
}
