package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultUsageHistoryPOJO {
    String txnNumber;
    String dateTime;
    String bundleName;
    String usedData;
    String type;
    String typeInfo;
    String charges;
    String txnAmount;
    String startBalance;
    String endBalance;
    String callTo;
    String callDuration;
    String smsTo;
    String transactionAmountDetails;
    String time;
    ArrayList<ExpendDataPOJO> expandedData;
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

