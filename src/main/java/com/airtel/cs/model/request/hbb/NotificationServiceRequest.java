package com.airtel.cs.model.request.hbb;

import com.airtel.cs.model.response.hbb.AlternateMsisdn;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificationServiceRequest {
    private String templateIdentifier;
    private String body;
    private String languageCode;
    private String searchId;
    private String templateChannel;
    private String templateSourceApp;
    private String sendNotificationType;
    private List<ReceiverId> receiverId;


}