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
public class EnterpriseKAM implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -6902714337850306096L;
  public String kamName;
  public String kamAuuid;
  public String kamEmailID;
  public String kamPhoneNumber;

}
