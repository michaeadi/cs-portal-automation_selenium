package com.airtel.cs.model.cs.response.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseSearchResponse implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -271224857376133876L;

  private EnterpriseAccount enterpriseAccount;
  private EnterpriseContactPerson enterpriseContactPerson;
  private EnterpriseKAM kam;

}
