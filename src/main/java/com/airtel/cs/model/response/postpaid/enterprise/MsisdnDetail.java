package com.airtel.cs.model.response.postpaid.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsisdnDetail {
    public String msisdn;
    public String simNumber;
    public String activationDate;
    public String lineType;
    public String paymentLevel;
    public String gsmStatus;
    public String currentUsagelimit;
    public String vip;
    public String cug;
    public String dnd;
    public String securityDeposit;
}
