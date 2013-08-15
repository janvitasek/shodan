package com.trioptimum.shodan.dispatch.service;

import com.trioptimum.shodan.lookup.api.Key;

public interface Dispatch {

	Object dispatch(Key key, Object... params) throws Exception;
}
