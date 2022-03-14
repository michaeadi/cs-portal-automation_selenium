package com.airtel.cs.model.cs.response.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseContactPerson implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -7953784872761550365L;
  private String contactPersonfirstName;
  private String contactPersonlastName;
  private String contactPersonEmailId;
  private String contactPersonMsisdn;

}
