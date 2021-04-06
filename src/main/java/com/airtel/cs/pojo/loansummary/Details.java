package com.airtel.cs.pojo.loansummary;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Details {
    private String vendorName;
    private Double loanAmount;
    private OutStanding currentOutstanding;
}
