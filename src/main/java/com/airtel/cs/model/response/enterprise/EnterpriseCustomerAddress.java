package com.airtel.cs.model.response.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseCustomerAddress implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -2330653091409435982L;
  private String addressLine1;
  private String addressLine2;
  private String suburb;
  private String city;
  private String state;
  private String country;
  private String postalCode;
}
