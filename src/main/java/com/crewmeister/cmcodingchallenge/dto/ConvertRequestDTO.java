package com.crewmeister.cmcodingchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConvertRequestDTO {

    private String date;
    @NotNull(message = "The Currency is required.")
    private String currency;
    @NotNull(message = "The Amount is required.")
    private String amount;
}
