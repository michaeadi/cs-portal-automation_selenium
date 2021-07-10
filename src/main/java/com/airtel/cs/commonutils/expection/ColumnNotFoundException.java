package com.airtel.cs.commonutils.expection;

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
