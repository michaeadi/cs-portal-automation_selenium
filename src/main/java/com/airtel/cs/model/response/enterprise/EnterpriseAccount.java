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
public class EnterpriseAccount implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 4230391191836711660L;
  public String enterpriseAccountNo;
  public String enterpriseAccountName;
  public String enterpriseAccountType;
  public String enterpriseBillingAcountId;
  public String enterpriseParentAccountNo;
  public String enterpriseParentAccountName;
  public String enterprisePersonId;
  public String enterpriseInvoicedAccount;
  public String enterpriseSegment;
  public String enterpriseSubSegment;
  public String enterpriseCustomerCategory;
  public Long enterpriseActivationDate;
  public Long enterpriseModifiedDate;
  public EnterpriseCustomerAddress enterpriseCustomerAddress;
}
