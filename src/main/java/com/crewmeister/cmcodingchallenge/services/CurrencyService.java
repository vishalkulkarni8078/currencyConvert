package com.crewmeister.cmcodingchallenge.services;

import com.crewmeister.cmcodingchallenge.dto.APISuccessDTO;
import com.crewmeister.cmcodingchallenge.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class CurrencyService {

    @Autowired private ModelMapper modelMapper;
    @Autowired private ObjectMapper objectMapper;
    @Value( "${exchange.rate.api.base.url}" ) private String baseUri;
    @Value( "${exchange.rate.api.access.key}" ) private String accessKey;

    public Set<String> getAvailableCurrencies()  {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Content-type","application/json")
                .uri(URI.create(baseUri+"latest?access_key="+accessKey))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder().build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            if(response.statusCode()==200){
                APISuccessDTO apiSuccessDTO=objectMapper.readValue(response.body(),APISuccessDTO.class);
                return apiSuccessDTO.getRates().keySet();
            }else {
                return new HashSet<String>();
            }
        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return new HashSet<String>();
        }



    }

}
