package com.airtel.cs.commonutils.applicationutils.enums;

public enum TicketSortType {
    ASC("ASC"), DESC("DESC");
    private String code;

    private TicketSortType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}