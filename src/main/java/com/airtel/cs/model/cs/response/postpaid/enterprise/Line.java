package com.airtel.cs.model.cs.response.postpaid.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Line {
    public String simSerialNumber;
    public String msisdn;
    public String accountNo;
    public String subscriberName;
    public String activationDate;
    public String lineNumber;
    public String lineType;
    public String paymentLevel;
    public String serviceType;
    public String status;
    public String currentUsagelimit;
    public Object basePlan;
    public Object addOnPlans;
    public String cug;
    public String dnd;
    public String vip;
    public Integer securityDeposit;
}
