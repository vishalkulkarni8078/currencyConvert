package com.crewmeister.cmcodingchallenge.controller;


import com.crewmeister.cmcodingchallenge.dto.ApiResponse;
import com.crewmeister.cmcodingchallenge.dto.ErrorResponse;
import com.crewmeister.cmcodingchallenge.services.CurrencyService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/exchangeRate")
@CommonsLog
public class ExchangeRateController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("/getOrders")
    ApiResponse<String> getOrders(@RequestBody Optional<String> request){
        return ApiResponse.success("test");
    }

    @GetMapping(value = "/getAvailableCurrencies")
    public ApiResponse<Set<String>> getAvailableCurrencies() {
        Set<String> availableCurrencies=currencyService.getAvailableCurrencies();
        return availableCurrencies.size()>0 ?  ApiResponse.success(availableCurrencies):  ApiResponse.error(ErrorResponse.builder().code("500").message("Internal Server Error").build());

    }

}
