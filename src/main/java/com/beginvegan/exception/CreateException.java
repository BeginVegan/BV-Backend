package com.beginvegan.exception;

public class CreateException extends Exception {

    private static final long serialVersionUID = 1L;

    public CreateException() {
    }

    public CreateException(String msg) {
        super(msg);
    }
}
