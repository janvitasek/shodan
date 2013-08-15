package com.trioptimum.shodan.common.internal;

public final class Call {

	private final CallablePoint target;

	private final Object[] parameters;

    private final CallMetadata metadata;

    public Call(CallablePoint target, Object[] parameters) {
        this(target, parameters, CallMetadata.getNullObject());
    }

	public Call(CallablePoint target, Object[] parameters, CallMetadata metadata) {
		this.target = target;
		this.parameters = (parameters != null) ? parameters.clone() : new Object[0];
        this.metadata = metadata;
	}

	public CallablePoint getTarget() {
		return target;
	}
	
	public Object[] getParameters() {
		return parameters;
	}

    public CallMetadata getMetadata() {
        return metadata;
    }
}
