package com.covid.tracker.entity;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * A ApiResponseDTO
 */
public class ApiResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    private ApiResponseDTO(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseEntity<ApiResponseDTO> response(final HttpStatus status, final String msg) {
        final ApiResponseDTO response = new ApiResponseDTO(status.name(), msg);
        return ResponseEntity.status(status).header(status.getReasonPhrase(), msg).body(response);
    }
}