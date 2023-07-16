package com.example.demo.service;

public class PurchaseIntervalNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PurchaseIntervalNotFoundException() {
	}

	public PurchaseIntervalNotFoundException(String message) {
		super(message);
	}

	public PurchaseIntervalNotFoundException(Throwable cause) {
		super(cause);
	}

	public PurchaseIntervalNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PurchaseIntervalNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
