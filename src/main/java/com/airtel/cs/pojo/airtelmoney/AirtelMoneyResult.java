package com.airtel.cs.pojo.airtelmoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirtelMoneyResult {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalCount;
    private ArrayList<AirtelMoneyData> data;
    private String startDate;
    private String endDate;
}
