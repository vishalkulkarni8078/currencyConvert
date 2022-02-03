package com.crewmeister.cmcodingchallenge.services;

import com.crewmeister.cmcodingchallenge.configuration.CustomException;
import com.crewmeister.cmcodingchallenge.dto.APISuccessDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.UUID;

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
                    .uri(URI.create(baseUri+"latest?access_key="+accessKey))
                    .build();
            HttpResponse<String> response = HttpClient.newBuilder().build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                APISuccessDTO apiSuccessDTO=objectMapper.readValue(response.body(),APISuccessDTO.class);
                return apiSuccessDTO.getRates().keySet();
            }else {
                throw new CustomException(String.format("Currency API failed with: %s", response.statusCode()));
            }
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }
    }

}
