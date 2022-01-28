package com.crewmeister.cmcodingchallenge.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @param <T>
 *  Common class for all apis success and error response.
 */
@Data
@Builder
public class ApiResponse<T> {
    @Builder.Default
    private boolean success = true;
    private T data;
    private ErrorResponse errorResponse;

    /**
     * @param data
     * @param <T>
     * @return creates success response
     */
    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder().data(data).build();
    }

    /**
     * @param isSuccess
     * @return void response
     */
    public static ApiResponse<Void> voidResponse(boolean isSuccess) { return ApiResponse.<Void>builder().success(isSuccess).build();}

    /**
     * @param errorCode
     * @param <T>
     * @return using error code returns error response
     */
    public static <T> ApiResponse<T> errorCode(String errorCode){
        return ApiResponse.<T>builder()
                .success(false)
                .errorResponse(ErrorResponse.builder().code(errorCode).build()).build();
    }

    /**
     * @param errorResponse
     * @param <T>
     * @return returns error response
     */
    public static <T> ApiResponse<T> error(ErrorResponse errorResponse){
        return ApiResponse.<T>builder()
                .success(false)
                .errorResponse(errorResponse).build();
    }
}
