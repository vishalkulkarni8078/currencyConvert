package com.crewmeister.cmcodingchallenge.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Use this class to create error response
 */
@Data
@Builder
public class ErrorResponse {
    private String referenceCode;
    private String message;
    private List<String> errors;
}
