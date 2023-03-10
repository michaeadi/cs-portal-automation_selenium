package com.airtel.cs.model.cs.response.offerdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferResponse {
    private Integer offerId;
    private String offerName;
    private String productID;
    private String offerstartDate;
    private String offerExpiryDate;
    private String offerType;
    private String offerState;
    private String pamServiceId;
    private Integer noOfDAs;
    private List<AccountInformation> accountInformation;
}
