package com.productmanager.data;

public class ProdManException extends Exception{
    public ProdManException() {
        super();
    }

    public ProdManException(String message) {
        super(message);
    }

    public ProdManException(String message, Throwable cause) {
        super(message, cause);
    }
}
