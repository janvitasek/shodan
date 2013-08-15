package com.trioptimum.shodan.lookup.internal;

import com.trioptimum.shodan.common.internal.Binding;
import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.lookup.service.LookupRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 24.11.12
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
public final class BindingContext {

    private final Binding binding;

    private final List<LookupRule> previouslyMatchedRules;

    private final List<CallablePoint> bindings;

    public BindingContext(Binding binding, List<LookupRule> previouslyMatchedRules, List<CallablePoint> bindings) {
        this.binding = binding;
        this.previouslyMatchedRules = Collections.unmodifiableList(new ArrayList<LookupRule>(previouslyMatchedRules));
        this.bindings = Collections.unmodifiableList(new ArrayList<CallablePoint>(bindings));
    }

    public Binding getBinding() {
        return binding;
    }

    public List<LookupRule> getPreviouslyMatchedRules() {
        return previouslyMatchedRules;
    }

    public List<CallablePoint> getPreviousBindings() {
        return bindings;
    }
}
