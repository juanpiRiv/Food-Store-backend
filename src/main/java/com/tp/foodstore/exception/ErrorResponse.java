package com.tp.foodstore.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Respuesta de error uniforme para toda la API.
 */
@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
