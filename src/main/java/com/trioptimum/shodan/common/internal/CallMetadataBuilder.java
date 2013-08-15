package com.trioptimum.shodan.common.internal;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 11.12.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public class CallMetadataBuilder {

    private Class<?> source;

    private Annotation[] metadata;

    public CallMetadataBuilder setSource(Class<?> source) {
        this.source = source;
        return this;
    }

    public CallMetadataBuilder setMarkers(Annotation... markers) {
        this.metadata = markers;
        return this;
    }

    public CallMetadata build() {
        return new CallMetadata(source, metadata);
    }
}
