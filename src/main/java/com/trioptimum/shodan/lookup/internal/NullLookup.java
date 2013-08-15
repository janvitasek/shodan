package com.trioptimum.shodan.lookup.internal;

import com.trioptimum.shodan.common.internal.Bindings;
import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.common.service.Function;
import com.trioptimum.shodan.lookup.api.Key;

import java.util.Collections;

public final class NullLookup implements Function<Key, Bindings> {

	public Bindings apply(Key key) {
		return new Bindings(key, Collections.<CallablePoint>emptyList());
	}
}
