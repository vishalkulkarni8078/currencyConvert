package com.crewmeister.cmcodingchallenge.controller;


import com.crewmeister.cmcodingchallenge.dto.ApiResponse;
import com.crewmeister.cmcodingchallenge.dto.ConvertRequestDTO;
import com.crewmeister.cmcodingchallenge.dto.ConvertResponseDTO;
import com.crewmeister.cmcodingchallenge.dto.DateRateDTO;
import com.crewmeister.cmcodingchallenge.services.CurrencyService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/exchangeRate")
@CommonsLog
public class ExchangeRateController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping(value = "/getAvailableCurrencies")
    public ResponseEntity<ApiResponse<Set<String>>> getAvailableCurrencies() {
        Set<String> availableCurrencies=currencyService.getAvailableCurrencies();
        return new ResponseEntity<>(ApiResponse.success(availableCurrencies), HttpStatus.OK);
    }

    @GetMapping(value = "/getAvailableRates")
    public ResponseEntity<ApiResponse<Map<String, Map<String, Double>>>> getAvailableExchangeRates() {
        Map<String, Map<String, Double>> availableExchangeRates=currencyService.getAvailableExchangeRates();
        return new ResponseEntity<>(ApiResponse.success(availableExchangeRates), HttpStatus.OK);
    }

    @GetMapping("/getRate/{date}")
    public ResponseEntity<ApiResponse<DateRateDTO>> getRateForDate(@PathVariable String date){
        DateRateDTO dateRateDTO=currencyService.getRateForGivenDate(date);
        return new ResponseEntity<>(ApiResponse.success(dateRateDTO), HttpStatus.OK);
    }

    @PostMapping("/getAmount")
    public ResponseEntity<ApiResponse<ConvertResponseDTO>> convertAmount(@RequestBody @Valid ConvertRequestDTO convertRequestDTO){
        ConvertResponseDTO convertedAmount=currencyService.getConvertedAmount(convertRequestDTO);
        return new ResponseEntity<>(ApiResponse.success(convertedAmount), HttpStatus.OK);
    }

}
