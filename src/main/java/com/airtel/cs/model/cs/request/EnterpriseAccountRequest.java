package com.airtel.cs.model.cs.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseAccountRequest implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -7208595532074671143L;
  public String accountNo;
  public String custMobileNo;
  public Boolean showVIP = true;
  public Integer limit = 1;
  public Integer offset = 0;

}
