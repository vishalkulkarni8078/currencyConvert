package com.crewmeister.cmcodingchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Use this class to create error response
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIErrorDTO {
    private Map<String,String> error;
}
