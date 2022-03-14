package com.airtel.cs.model.cs.response.am;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceList {
    private String name;
    private String dailyCount;
    private String dailyAmountLabel;
    private String weeklyCount;
    private String weeklyAmountLabel;
    private String perTxnAmountLabel;
    private String monthlyCount;
    private String monthlyAmountLabel;
    private String annualCount;
    private String annualAmountLabel;
}
