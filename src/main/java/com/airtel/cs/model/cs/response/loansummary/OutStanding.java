package com.airtel.cs.model.cs.response.loansummary;

import com.airtel.cs.model.cs.response.vendors.HeaderList;
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
public class OutStanding {
    private String value;
    private List<HeaderList> headerList;
}
