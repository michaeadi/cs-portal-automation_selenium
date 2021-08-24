package com.airtel.cs.commonutils.dataproviders.databeans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthTabDataBeans {
    private String policyName;
    private String policyMessage;
    private String minAnswer;
    private List<String> questions;
}
