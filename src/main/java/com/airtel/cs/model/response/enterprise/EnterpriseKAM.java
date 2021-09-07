package com.airtel.cs.model.response.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseKAM implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -6902714337850306096L;
  private String kamName;
  private String kamAuuid;
  private String kamEmailID;
  private String kamPhoneNumber;

}
