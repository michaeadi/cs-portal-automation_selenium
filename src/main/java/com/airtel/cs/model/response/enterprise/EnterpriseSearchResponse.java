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
public class EnterpriseSearchResponse implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -271224857376133876L;

  public EnterpriseAccount enterpriseAccount;
  public EnterpriseContactPerson enterpriseContactPerson;
  public EnterpriseKAM kam;

}
