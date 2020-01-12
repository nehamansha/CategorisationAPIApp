package com.categorisationapi.response;

public enum TransactionStatusEnum {

    BOOKED("BOOKED"),
    PENDING("PENDING");

    private String value;

    TransactionStatusEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
