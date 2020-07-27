package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultRechargeHistoryPOJO {
    String charges;
    String dateTime;
    String bundleName;
    String transactionId;
    String status;
    rechargeBenefitPOJO rechargeBenefit;
}