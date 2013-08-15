package com.trioptimum.shodan.facade.api;

import com.trioptimum.shodan.common.internal.CallMetadata;
import com.trioptimum.shodan.delegate.api.DispatchDelegateFactory;
import com.trioptimum.shodan.delegate.service.DelegateFactory;
import com.trioptimum.shodan.dispatch.api.DispatchBuilder;
import com.trioptimum.shodan.facade.internal.DefaultConfiguration;
import com.trioptimum.shodan.facade.service.Delegation;
import com.trioptimum.shodan.facade.service.Settings;
import com.trioptimum.shodan.intercept.api.InterceptionByRule;
import com.trioptimum.shodan.invocation.api.InterceptableDispatchInvocation;
import com.trioptimum.shodan.lookup.api.LookupByRules;
import com.trioptimum.shodan.lookup.service.StoringLookup;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 1.12.12
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class Shodan implements Delegation {

    private final StoringLookup storingLookup;

    private final DelegateFactory delegateFactory;

    public Shodan(Settings... settings) {
        DefaultConfiguration conf = new DefaultConfiguration();
        for (Settings s : settings) {
            s.configure(conf);
        }

        this.storingLookup = new LookupByRules(conf.getLookupRules());
        this.delegateFactory = new DispatchDelegateFactory(
                new DispatchBuilder().setLookup(storingLookup)
                                     .setInvocation(new InterceptableDispatchInvocation(new InterceptionByRule(conf.getInterceptionRules())))
                                     .build());
    }

    public Shodan add(Object object) {
        storingLookup.add(object);
        return this;
    }

    public Shodan addWeakly(Object object) {
        storingLookup.addWeakly(object);
        return this;
    }

    public Shodan addAll(Collection<?> objects) {
        storingLookup.addAll(objects);
        return this;
    }

    public Shodan addAllWeakly(Collection<?> objects) {
        storingLookup.addAllWeakly(objects);
        return this;
    }

    public Shodan remove(Object object) {
        storingLookup.remove(object);
        return this;
    }

    public Shodan removeAll(Collection<?> objects) {
        storingLookup.removeAll(objects);
        return this;
    }

    public <D> D createDelegate(Class<D> delegateClass, CallMetadata metadata) {
        return delegateFactory.createDelegate(delegateClass, metadata);
    }
}
