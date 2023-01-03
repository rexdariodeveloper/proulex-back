package com.pixvs.spring.handler.exceptions;

public class AdvertenciaException extends Exception {
    public AdvertenciaException(String message) {
        super(message);
    }

    public AdvertenciaException(Exception ex) {
        super(ex.getCause() != null ? (ex.getCause().getCause() != null ? ex.getCause().getCause().getMessage() : ex.getCause().getMessage()) : ex.getMessage());
    }
}