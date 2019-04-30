package io;

public class OverReadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected OverReadException(String str) {
		super(str);
	}

}
