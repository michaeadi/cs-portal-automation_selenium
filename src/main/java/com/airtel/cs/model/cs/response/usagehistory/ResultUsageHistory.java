package com.airtel.cs.model.cs.response.usagehistory;

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
public class ResultUsageHistory {
    private String txnNumber;
    private String dateTime;
    private String bundleName;
    private String usedData;
    private String type;
    private String typeInfo;
    private String charges;
    private String txnAmount;
    private String startBalance;
    private String description;
    private String addOrSubtract;
    private String endBalance;
    private String callTo;
    private String callDuration;
    private String smsTo;
    private String transactionAmountDetails;
    private String time;
    private List<ExpendData> expandedData;
}
//"txnNumber": "306701202007241016311000000417789510200000000000000000000CCNnkuocc3b-0000000",
//      "dateTime": "1595605591000",
//      "bundleName": "AIRTIME",
//      "usedData": "0.0007 MB",
//      "type": "DATA",
//      "typeInfo": "Data Used - 0.0007 MB",
//      "charges": "0.84 KES",
//      "txnAmount": "0.84 KES",
//      "startBalance": "131.33 KES",
//      "endBalance": "130.49 KES"

