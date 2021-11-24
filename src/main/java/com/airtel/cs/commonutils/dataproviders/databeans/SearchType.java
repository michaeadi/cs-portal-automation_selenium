package com.airtel.cs.commonutils.dataproviders.databeans;


public enum SearchType {

  CONTAINS("CONTAINS"), RANGE("RANGE"), LESS_THAN("LESS_THAN"), GREATER_THAN("GREATER_THAN"), LIKE("LIKE"), DATE("DATE");

  private String code;

  private SearchType(String code) {
    this.code = code;
  }

  public String code() {
    return code;
  }
}
