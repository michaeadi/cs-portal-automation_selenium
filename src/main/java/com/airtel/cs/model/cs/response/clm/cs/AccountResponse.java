package com.airtel.cs.model.cs.response.clm.cs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse implements Serializable {
    private String channel;
    private String category;
    private String status;
    private String createdOn;
    private String createdBy;
    private String modifiedOn;
    private String modifiedBy;
    private String id;
    private String isSecurityQuestionSet;
    private Integer securityQuestionsConfigured;
    private String barred;
    private BarDetail barredDetails;
}