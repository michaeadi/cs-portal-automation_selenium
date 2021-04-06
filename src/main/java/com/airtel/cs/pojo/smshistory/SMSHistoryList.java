package com.airtel.cs.pojo.smshistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
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
    private boolean action;
    private String templateChannel;
    private String templateIdentifier;
}
