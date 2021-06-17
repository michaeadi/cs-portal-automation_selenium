package com.airtel.cs.pojo.response.offerdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInformation {
    private Integer daId;
    private String daStartDate;
    private String daEndDate;
    private Integer offerId;
    private Integer daAmount;
    private Integer productId;
}
