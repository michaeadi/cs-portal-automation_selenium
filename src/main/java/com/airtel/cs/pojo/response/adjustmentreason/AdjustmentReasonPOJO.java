package com.airtel.cs.pojo.response.adjustmentreason;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdjustmentReasonPOJO {
    private String status;
    private String statusCode;
    private List<ReasonDetail> result;
}
