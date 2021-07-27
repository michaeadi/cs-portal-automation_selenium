package com.airtel.cs.commonutils.dataproviders.databeans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssignmentQueueRuleDataBeans {
    private String categoryCode;
    private String attributeName;
    private String attributeValue;
    private String workgroupName;
    private String queueName;
    private String ticketPriority;
    private String ticketState;
    private String rulePriority;
}
