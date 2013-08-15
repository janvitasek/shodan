package com.trioptimum.shodan.common.internal;

public final class Return {

	private final Object value;

	private final Exception exception;

	public Return(Object value) {
		this(value, null);
	}

	public Return(Exception exception) {
		this(null, exception);
	}

	private Return(Object value, Exception exception) {
		this.value = value;
		this.exception = exception;
	}

	public Object getValue() {
		return value;
	}

	public Exception getException() {
		return exception;
	}

	public boolean isExceptional() {
		return (exception != null);
	}
}
