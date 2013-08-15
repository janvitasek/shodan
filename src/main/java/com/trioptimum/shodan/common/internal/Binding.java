package com.trioptimum.shodan.common.internal;

import com.trioptimum.shodan.lookup.api.Key;

public final class Binding {

	private final Key key;
	
	private final CallablePoint destination;

	public Binding(Key key, CallablePoint destination) {
		this.key = key;
		this.destination = destination;
	}

	public Key getKey() {
		return key;
	}

	public CallablePoint getDestination() {
		return destination;
	}
}