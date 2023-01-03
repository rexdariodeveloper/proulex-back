package com.pixvs.spring.handler.exceptions;

public class ErrorConDatosException extends Exception {
    public ErrorConDatosException(String message) {
        super(message);
    }

    public ErrorConDatosException(Exception ex) {
        super(ex.getCause() != null ? (ex.getCause().getCause() != null ? ex.getCause().getCause().getMessage() : ex.getCause().getMessage()) : ex.getMessage());
    }
}