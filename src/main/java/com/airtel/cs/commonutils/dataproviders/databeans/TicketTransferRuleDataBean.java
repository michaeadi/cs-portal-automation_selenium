package com.airtel.cs.commonutils.dataproviders.databeans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketTransferRuleDataBean {
    private String issueCode;
    private String ticketFromState;
    private String ticketToState;
    private String fromQueue;
    private String toQueue;
}
