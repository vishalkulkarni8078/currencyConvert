package com.crewmeister.cmcodingchallenge.services;

import com.crewmeister.cmcodingchallenge.configuration.CustomException;
import com.crewmeister.cmcodingchallenge.dto.APIErrorDTO;
import com.crewmeister.cmcodingchallenge.dto.DateRateDTO;
import com.crewmeister.cmcodingchallenge.dto.LatestRatesDTO;
import com.crewmeister.cmcodingchallenge.dto.TimeSeriesDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@CommonsLog
public class CurrencyService {

    @Autowired private ObjectMapper objectMapper;
    @Value( "${exchange.rate.api.base.url}" ) private String baseUri;
    @Value( "${exchange.rate.api.access.key}" ) private String accessKey;

    public Set<String> getAvailableCurrencies()  {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("Content-type","application/json")
                    .uri(URI.create(baseUri+"latest?access_key="+accessKey+"&base=EUR"))
                    .build();
            HttpResponse<String> response = HttpClient.newBuilder().build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                LatestRatesDTO latestRatesDTO =objectMapper.readValue(response.body(), LatestRatesDTO.class);
                return latestRatesDTO.getRates().keySet();
            }else {
                APIErrorDTO apiErrorDTO=objectMapper.readValue(response.body(), APIErrorDTO.class);
                throw new CustomException(String.format("[API Error: %s]Error Code: %s Message: %s ", response.statusCode(),apiErrorDTO.getError().get("code"),apiErrorDTO.getError().get("message")));
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public Map<String, Map<String, Double>> getAvailableExchangeRates() {
        Calendar calendar = Calendar.getInstance();
        Date today=calendar.getTime();
        calendar.add(Calendar.YEAR, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder builder=new StringBuilder();
        builder.append(baseUri)
                .append("timeseries?access_key=")
                .append(accessKey)
                .append("&start_date=")
                .append(dateFormat.format(calendar.getTime()))
                .append("&end_date=")
                .append(dateFormat.format(today))
                .append("&base=EUR");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("Content-type","application/json")
                    .uri(URI.create(builder.toString()))
                    .build();
            HttpResponse<String> response = HttpClient.newBuilder().build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                TimeSeriesDTO latestRatesDTO =objectMapper.readValue(response.body(), TimeSeriesDTO.class);
                return latestRatesDTO.getRates();
            }else {
                APIErrorDTO apiErrorDTO=objectMapper.readValue(response.body(), APIErrorDTO.class);
                throw new CustomException(String.format("[API Error: %s]Error Code: %s Message: %s ", response.statusCode(),apiErrorDTO.getError().get("code"),apiErrorDTO.getError().get("message")));
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public DateRateDTO getRateForGivenDate(String date) {
        if(validateDate(date)){
            StringBuilder builder=new StringBuilder();
            builder.append(baseUri)
                    .append(date)
                    .append("?access_key=")
                    .append(accessKey)
                    .append("&base=EUR");
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .header("Content-type","application/json")
                        .uri(URI.create(builder.toString()))
                        .build();
                HttpResponse<String> response = HttpClient.newBuilder().build()
                        .send(request, HttpResponse.BodyHandlers.ofString());
                if(response.statusCode()==200){
                    return objectMapper.readValue(response.body(), DateRateDTO.class);
                }else {
                    APIErrorDTO apiErrorDTO=objectMapper.readValue(response.body(), APIErrorDTO.class);
                    throw new CustomException(String.format("[API Error: %s]Error Code: %s Message: %s ", response.statusCode(),apiErrorDTO.getError().get("code"),apiErrorDTO.getError().get("message")));
               }
            }catch (Exception e){
                throw new CustomException(e.getMessage());
            }
        }else {
            throw new CustomException(String.format("Invalid date provided: %s, provide date in yyyy-MM-dd format", date));
        }
    }

    private boolean validateDate(String date){
       return GenericValidator.isDate(date, "yyyy-MM-dd", true);
    }
}
