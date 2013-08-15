package com.trioptimum.shodan.common.internal;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 2/20/13
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallMetadata {

    private final Class<?> source;

    private final Annotation[] markers;

    CallMetadata(Class<?> source, Annotation[] markers) {
        this.source = source;
        this.markers = markers;
    }

    public static CallMetadata getNullObject() {
        return new CallMetadata(null, new Annotation[0]);
    }

    public Class<?> getSource() {
        return source;
    }

    public Annotation[] getMarkers() {
        return markers;
    }
}
