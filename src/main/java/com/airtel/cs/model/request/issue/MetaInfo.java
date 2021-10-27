package com.airtel.cs.model.request.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaInfo {
    private String lineType;
    private String isVip;
    private String segment;
    private String subsegment;
    private String serviceCategory;
    private String source1;
    private String source2;
    private String alternateNumber;
    private String clientName;
    private String customerType;
    private String interactionChannel1;
    private String isHBBProfile;
}
