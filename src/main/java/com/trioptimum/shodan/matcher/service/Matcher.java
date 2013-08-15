package com.trioptimum.shodan.matcher.service;

public interface Matcher<T> {

	boolean matches(T tested);
}
