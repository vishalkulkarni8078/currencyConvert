package com.crewmeister.cmcodingchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConvertResponseDTO {
    private String actualAmount;
    private String from;
    private String to;
    private String convertedAmount;
    private String date;
    private String rate;
}
