package com.airtel.cs.commonutils.exceptions;

public class ColumnNotFoundException extends Exception {
    private String exceptionMessage;
    public ColumnNotFoundException(String exceptionMessage) {
        this.exceptionMessage =exceptionMessage;
    }

    @Override
    public String toString(){
        return exceptionMessage;
    }
}
