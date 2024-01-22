package com.grace.receiptprocessor.common.exception;

import com.grace.receiptprocessor.common.enums.Status;
import lombok.Getter;

@Getter
public class IdNotFoundException extends RuntimeException{
    @Getter
    private int code;

    public IdNotFoundException(int code, String message) {
        super(message);
        this.code = code;
    }

    public IdNotFoundException(Status status) {
        this(status.getCode(), status.getMessage());
    }

    public IdNotFoundException(Status status, String msg) {
        this(status.getCode(), msg);
    }

    public static IdNotFoundException exception(Status status){
        return new IdNotFoundException(status);
    }
}