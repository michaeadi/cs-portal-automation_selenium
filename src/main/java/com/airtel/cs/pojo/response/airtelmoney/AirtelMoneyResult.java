package com.airtel.cs.pojo.response.airtelmoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirtelMoneyResult {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalCount;
    private ArrayList<AirtelMoneyData> data;
    private String startDate;
    private String endDate;
}
