package com.airtel.cs.commonutils.exceptions;

public class RuleNotFoundException extends Exception {
    private String exceptionMessage;
    public RuleNotFoundException(String exceptionMessage) {
        this.exceptionMessage =exceptionMessage;
    }

    @Override
    public String toString(){
        return exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage;
    }

}
