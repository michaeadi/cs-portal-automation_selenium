package com.airtel.cs.pojo.vendors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vendors {
    private String vendorName;
    private String loanSummary;
    private String loanDetail;
}
