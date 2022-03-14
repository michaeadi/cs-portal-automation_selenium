package com.airtel.cs.model.cs.response.clm.downstream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscriptions {

    private String connectionType;
    private String channel;
    private String category;
    private Boolean pinReset;
    private Boolean pinSet;
    private String id;
    private String latitude;
    private String longitude;
    private String status;
    private String createdOn;
    private String createdBy;
    private String modifiedOn;
    private String modifiedBy;
    private String kycStatus;
    private String branchCode;
    private Boolean isSecurityQuestionSet;
    private Integer securityQuestionsConfigured;
    private String tcpId;
    private Boolean barred;
    private BarDetail barredDetails;

}