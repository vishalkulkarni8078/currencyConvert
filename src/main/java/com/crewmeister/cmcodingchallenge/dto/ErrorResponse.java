package com.crewmeister.cmcodingchallenge.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Use this class to create error response
 */
@Data
@Builder
public class ErrorResponse {
    private String code;
    private String message;
}
