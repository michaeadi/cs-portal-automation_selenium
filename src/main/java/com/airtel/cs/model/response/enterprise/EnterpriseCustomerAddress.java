package com.airtel.cs.model.response.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseCustomerAddress implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -2330653091409435982L;
  public String addressLine1;
  public String addressLine2;
  public String suburb;
  public String city;
  public String state;
  public String country;
  public String postalCode;
}
