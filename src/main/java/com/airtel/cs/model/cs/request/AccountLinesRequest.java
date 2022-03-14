package com.airtel.cs.model.cs.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountLinesRequest {
    private static final long serialVersionUID = 7392829029566524407L;

    private String accountNo;

    private String msisdn;

    private List<String> serviceTypes;

    private String lineType;

    private String limit;

    private String offset;

    private boolean showVIP = true;
}
