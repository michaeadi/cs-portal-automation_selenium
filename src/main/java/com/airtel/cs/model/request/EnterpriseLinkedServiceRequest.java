package com.airtel.cs.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseLinkedServiceRequest implements Serializable {

  /**
   * This is a postpaid linked msisdn request class for API call
   */
  private static final long serialVersionUID = -889979218386937934L;

  private String accountNo;
  private String msisdn;
  private String serviceType;
  private String lineType;

  /**
   * pageSize variable is for mentioning the number of rows that has to come on a
   * page.
   */
  private Integer pageSize = 5;

  /**
   * startIndex is the offset value from where data has to fetch
   */
  private Integer pageNumber = 1;



}
