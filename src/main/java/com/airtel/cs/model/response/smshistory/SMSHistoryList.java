package com.airtel.cs.model.response.smshistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SMSHistoryList {

    private String smsType;
    private String date;
    private String languageCode;
    private String languageName;
    private String messageText;
    private String senderId;
    private String senderName;
    private String status;
    private String templateName;
    private Boolean action;
    private String templateChannel;
    private String templateIdentifier;
    private String alternateNumber;

}
