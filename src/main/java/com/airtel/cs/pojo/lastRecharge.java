package com.airtel.cs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class lastRecharge {
    String amount;
    long rechargeOn;
}

