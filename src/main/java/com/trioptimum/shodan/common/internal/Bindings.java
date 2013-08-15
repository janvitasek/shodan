package com.trioptimum.shodan.common.internal;

import com.trioptimum.shodan.lookup.api.Key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Bindings {

	private final Key key;
	
	private final List<CallablePoint> bindings;

	public Bindings(Key key, List<CallablePoint> bindings) {
		this.key = key;
		this.bindings = Collections.unmodifiableList(new ArrayList<CallablePoint>(bindings));
	}
	
	public Key getKey() {
		return key;
	}
	
	public List<CallablePoint> getBindings() {
		return bindings;
	}
}
