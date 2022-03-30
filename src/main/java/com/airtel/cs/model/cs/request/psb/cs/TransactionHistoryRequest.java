package com.airtel.cs.model.cs.request.psb.cs;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryRequest {
    private String msisdn;
    private Integer pageSize;
    private Integer pageNumber;
    private String nubanId;
    private String type;
}
