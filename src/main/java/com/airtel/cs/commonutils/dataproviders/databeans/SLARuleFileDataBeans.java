package com.airtel.cs.commonutils.dataproviders.databeans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SLARuleFileDataBeans {
    private String categoryCode;
    private String customerVip;
    private String lineType;
    private String customerType;
    private String serviceCategory;
    private String customerSegment;
    private String customerSubSegment;
    private String interactionChannel;
    private String workgroup1;
    private String sla1;
    private String workgroup2;
    private String sla2;
    private String workgroup3;
    private String sla3;
    private String workgroup4;
    private String sla4;
    private String committedSLA;
    private String defaultRule;
}
