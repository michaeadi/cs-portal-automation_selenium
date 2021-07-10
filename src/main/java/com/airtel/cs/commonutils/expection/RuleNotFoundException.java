package com.airtel.cs.commonutils.expection;

public class RuleNotFoundException extends Exception {
    private String exceptionMessage;
    public RuleNotFoundException(String exceptionMessage) {
        this.exceptionMessage =exceptionMessage;
    }

    @Override
    public String toString(){
        return exceptionMessage;
    }
}
