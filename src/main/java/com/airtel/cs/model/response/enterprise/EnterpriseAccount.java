package com.airtel.cs.model.response.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseAccount implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 4230391191836711660L;
  private String enterpriseAccountNo;
  private String enterpriseAccountName;
  private String enterpriseAccountType;
  private String enterpriseBillingAcountId;
  private String enterpriseParentAccountNo;
  private String enterpriseParentAccountName;
  private String enterprisePersonId;
  private String enterpriseInvoicedAccount;
  private String enterpriseSegment;
  private String enterpriseSubSegment;
  private String enterpriseCustomerCategory;
  private Long enterpriseActivationDate;
  private Long enterpriseModifiedDate;
  private EnterpriseCustomerAddress enterpriseCustomerAddress;
}
