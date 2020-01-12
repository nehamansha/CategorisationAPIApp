package com.categorisationapi.response;

public enum TransactionTypeEnum {

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private String value;

    TransactionTypeEnum(String value) {
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
