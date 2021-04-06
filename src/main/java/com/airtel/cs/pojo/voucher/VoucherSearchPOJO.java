package com.airtel.cs.pojo.voucher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoucherSearchPOJO {
    private String status;
    private int statusCode;
    private VoucherDetail result;
    private String message;
}
