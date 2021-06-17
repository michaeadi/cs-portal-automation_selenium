package com.airtel.cs.pojo.response.offerdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferDetailPOJO {

    private String message;
    private String status;
    private int statusCode;
    private ArrayList<OfferResponse> result;
}
