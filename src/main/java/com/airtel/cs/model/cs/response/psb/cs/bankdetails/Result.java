package com.airtel.cs.model.cs.response.psb.cs.bankdetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    private String accountNumber;
    private String branchCode;
    private String bankName;
    private String bankId;
    private String userGrade;
    private String currency;
    private String openingBalance;
    private String availableBalance;
    private String status;
    private String frozen;
    private String currentBalance;
    private String customerNo;
}
