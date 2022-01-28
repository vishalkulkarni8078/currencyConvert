package com.crewmeister.cmcodingchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APISuccessDTO {
    private Boolean success;
    private BigInteger timestamp;
    private String base;
    private String date;
    private Map<String,Double> rates;
}
