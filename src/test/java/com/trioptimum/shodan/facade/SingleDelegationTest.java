package com.trioptimum.shodan.facade;

import com.trioptimum.shodan.facade.service.AbstractSettings;
import com.trioptimum.shodan.helper.TestMatchers;
import org.testng.annotations.Test;

import static com.trioptimum.shodan.lookup.api.BindingContextMatchers.*;
import static com.trioptimum.shodan.matcher.api.BasicMatchers.is;
import static com.trioptimum.shodan.matcher.api.BasicMatchers.none;
import static com.trioptimum.shodan.matcher.api.CollectionMatchers.size;
import static com.trioptimum.shodan.matcher.api.NumberMatcher.sameOrGreaterThan;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 2.12.12
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
public class SingleDelegationTest extends DelegationTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void noBinding() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
            }
        });
        callAndAssertNoInvocation();
    }

    @Test
    public void destinationMatcher() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher());
            }
        });
        callAndAssertInvoked();
    }

    @Test
    public void destinationMatcherFalse() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(none());
            }
        });
        callAndAssertNoInvocation();
    }

    @Test
    public void callMatcher() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher()).to(TestMatchers.integerArgVoidCallMatcher());
            }
        });
        callAndAssertInvoked();
    }

    @Test
    public void callMatcherFalse() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher()).to(none());
            }
        });
        callAndAssertNoInvocation();
    }

    @Test
    public void bindingMatcher() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher()).when(binding(TestMatchers.integerArgVoidBindingMatcher(destination)));
            }
        });
        callAndAssertInvoked();
    }

    @Test
    public void bindingMatcherFalse() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher()).when(binding(none()));
            }
        });
        callAndAssertNoInvocation();
    }

    @Test
    public void prevRules() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher()).when(none()); // TODO never()?
                bind(integerArgVoidMatcher()).when(prevRulesMatched(size(is(1))));
            }
        });

        callAndAssertInvoked();
    }

    @Test
    public void prevRulesFalse() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher()).when(prevRulesMatched(size(sameOrGreaterThan(1))));
            }
        });

        callAndAssertNoInvocation();
    }

    @Test
    public void prevBindingsTrue() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher());
                bind(integerArgVoidMatcher()).when(prevBindings(size(is(1))));
            }
        });

        callAndAssertInvoked(2);
    }

    @Test
    public void prevBindingsFalse() {
        setup(new AbstractSettings() {
            @Override
            protected void configure() {
                bind(integerArgVoidMatcher()).when(prevBindings(size(sameOrGreaterThan(1))));
            }
        });

        callAndAssertNoInvocation();
    }
}
