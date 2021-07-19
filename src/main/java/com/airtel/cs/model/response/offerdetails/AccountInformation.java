package com.airtel.cs.model.response.offerdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountInformation {
    private Integer daId;
    private String daStartDate;
    private String daEndDate;
    private Integer offerId;
    private Integer daAmount;
    private Integer productId;
}
