package com.tp.foodstore.exception;

/**
 * Excepción de negocio: se lanza cuando una entidad solicitada no existe.
 */
public class NegocioException extends RuntimeException {

    public NegocioException(String message) {
        super(message);
    }
}
