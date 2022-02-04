package com.crewmeister.cmcodingchallenge.configuration;

import com.crewmeister.cmcodingchallenge.dto.ApiResponse;
import com.crewmeister.cmcodingchallenge.dto.ErrorResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Class handles exception globally
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@CommonsLog
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());


        String errorReference = UUID.randomUUID().toString().substring(0,8).toUpperCase();
        //logging error
        logError(ex, errorReference);
        return new ResponseEntity<>(ApiResponse.error(ErrorResponse.builder().referenceCode(errorReference).message("Arguments are not valid").errors(errors).build()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex){
        String errorReference = UUID.randomUUID().toString().substring(0,8).toUpperCase();
        //logging error
        logError(ex, errorReference);
        return new ResponseEntity<>(ApiResponse.error(ErrorResponse.builder().referenceCode(errorReference).message(ex.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(Exception ex, String reference){
            log.error(String.format("%s, Reference code: %s", HttpStatus.INTERNAL_SERVER_ERROR, reference), ex);
    }
}
