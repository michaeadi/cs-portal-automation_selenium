package com.airtel.cs.model.response.customeprofile;

import com.airtel.cs.model.response.kycprofile.PUK;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDetails {

  private String eventType;

  private String activationDate;

  private String sim;

  private String simType;

  private Set<PUK> puk;

  private String status;

  private String lineType;

  private String serviceCategory;

  private String segment;

  private String subSegment;

  private String serviceClass;

  private boolean isVIP;

  private Boolean gsmAdditionalInfo;

  private String modifiedBy;

  private String modifiedDate;

  private String reason;

  private String pin1;

  private String pin2;

  private String customerAccountNumber;

  private String customerType;
  
  private String email;
  
  private AtomicInteger kycResponsesRecieved;
}
