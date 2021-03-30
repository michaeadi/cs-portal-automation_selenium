package com.airtel.cs.commonutils.DataProviders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferQueueDataBean {
    private String fromQueue;
    private String toQueue;
    private String transferAnyway;
}
