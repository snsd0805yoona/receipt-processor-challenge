package com.grace.receiptprocessor.common.enums;

import lombok.Getter;


public enum Status {
    ID_NOT_FOUND(1004, "This id is not exist in db, please try another one.");
    @Getter
    private final Integer code;
    @Getter
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public static Status getStatusEnumByStatus(Integer statusCode) {

        if (statusCode == null) {
            return null;
        }

        for (Status statusEnum : values()) {
            if (statusEnum.getCode().equals(statusCode)) {
                return statusEnum;
            }
        }
        return null;
    }

}
