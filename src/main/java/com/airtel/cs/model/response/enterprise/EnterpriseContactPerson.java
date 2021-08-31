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
public class EnterpriseContactPerson implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -7953784872761550365L;
  public String contactPersonfirstName;
  public String contactPersonlastName;
  public String contactPersonEmailId;
  public String contactPersonMsisdn;

}
