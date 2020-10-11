package POJO.SMSHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
