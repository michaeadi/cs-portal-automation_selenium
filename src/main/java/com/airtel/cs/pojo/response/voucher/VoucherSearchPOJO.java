package com.airtel.cs.pojo.response.voucher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherSearchPOJO {
    private String status;
    private int statusCode;
    private VoucherDetail result;
    private String message;
}
