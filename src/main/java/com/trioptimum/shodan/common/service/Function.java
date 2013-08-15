package com.trioptimum.shodan.common.service;

public interface Function<I, O> {
	
	O apply(I input);
}
