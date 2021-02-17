package POJO.AirtelMoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirtelMoneyData {
    private String transactionId;
    private String transactionDate;
    private String service;
    private String source;
    private String msisdn;
    private Integer amount;
    private Integer serviceCharge;
    private Integer balanceBefore;
    private Integer balanceAfter;
    private String status;
    private String currencyType;
    private String txnType;
    private Boolean enableResendSms;
    private String secondPartyName;
}
