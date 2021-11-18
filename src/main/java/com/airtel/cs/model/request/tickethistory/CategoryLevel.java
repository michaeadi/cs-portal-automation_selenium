package com.airtel.cs.model.request.tickethistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CategoryLevel {
  
  private int id ;
  private String categoryName;
  private int level;
  
}
