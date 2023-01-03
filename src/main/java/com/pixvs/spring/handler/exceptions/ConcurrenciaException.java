package com.pixvs.spring.handler.exceptions;

/**
 * Created by David Arroyo on 15/02/2021.
 */
public class ConcurrenciaException extends Exception {
    public ConcurrenciaException(String message) {
        super(message);
    }
}
