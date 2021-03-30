package com.airtel.cs.pojo.LoanSummary;

import com.airtel.cs.pojo.Vendors.HeaderList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutStanding {
    private Double value;
    private ArrayList<HeaderList> headerList;
}
