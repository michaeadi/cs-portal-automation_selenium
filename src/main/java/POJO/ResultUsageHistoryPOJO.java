package POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "ResultUsageHistoryPOJO{" +
                "txnNumber='" + txnNumber + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", bundleName='" + bundleName + '\'' +
                ", usedData='" + usedData + '\'' +
                ", type='" + type + '\'' +
                ", typeInfo='" + typeInfo + '\'' +
                ", charges='" + charges + '\'' +
                ", txnAmount='" + txnAmount + '\'' +
                ", startBalance='" + startBalance + '\'' +
                ", endBalance='" + endBalance + '\'' +
                '}';
    }
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

