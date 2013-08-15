package com.trioptimum.shodan.matcher.internal;

import com.trioptimum.shodan.lookup.api.Key;
import com.trioptimum.shodan.matcher.service.Matcher;

public interface CallCheckRule {

	Matcher<? super Key> getKeyMatcher();
}
