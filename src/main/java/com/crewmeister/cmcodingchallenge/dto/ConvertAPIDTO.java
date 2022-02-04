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
public class ConvertAPIDTO {
    private Map<String,String> query;
    private Map<String, String> info;
    private String date;
    private String result;
}
