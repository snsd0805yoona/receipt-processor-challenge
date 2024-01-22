package com.grace.receiptprocessor.common;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ApiResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 3847543128873460119L;
    private Boolean success;
    private Integer statusCode;
    private String message;
    private T result;

    public static ApiResponse success() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage(HttpStatus.OK.getReasonPhrase());

        return apiResponse;
    }

    public static ApiResponse success(String message) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage(message);

        return apiResponse;
    }

    public static <T> ApiResponse<T> success(T result) {
        ApiResponse apiResponse = new ApiResponse<>();

        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage(HttpStatus.OK.getReasonPhrase());
        apiResponse.setResult(result);

        return apiResponse;
    }

    public static <T> ApiResponse<T> success(String message, T result) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage(message);
        apiResponse.setResult(result);

        return apiResponse;
    }

    public static ApiResponse error(HttpStatus httpStatus) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setStatusCode(httpStatus.value());
        apiResponse.setMessage(httpStatus.getReasonPhrase());

        return apiResponse;
    }

    public static ApiResponse error(HttpStatus httpStatus, String message) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setSuccess(Boolean.FALSE);
        apiResponse.setStatusCode(httpStatus.value());
        apiResponse.setMessage(message);

        return apiResponse;
    }
}
