package com.pixvs.spring.handler.exceptions;

/**
 * Created by Angel on 22/04/2019.
 */
public class UsuarioException extends Exception {
    public UsuarioException(String message_json) {
        super(message_json);
    }
}
