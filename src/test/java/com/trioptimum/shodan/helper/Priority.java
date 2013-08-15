package com.trioptimum.shodan.helper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* Created with IntelliJ IDEA.
* User: Turtles
* Date: 12.12.12
* Time: 16:50
* To change this template use File | Settings | File Templates.
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {

    int value();
}
