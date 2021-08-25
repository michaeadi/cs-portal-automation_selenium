package com.airtel.cs.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsageRequestV3DTO implements Serializable {

    private static final long serialVersionUID = -1055936282662967484L;
    private String msisdn;
    private Long startDate;
    private Long endDate;
    private String type;
    private String cdrType;
    private Integer offset;
    private Integer numberOfRecords;
    private String transactionCategory;
    private String summaryLevel;

}
