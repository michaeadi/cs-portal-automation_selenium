package com.airtel.cs.pojo.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigResult {
    private Map<String,String> authDataConfig;
    private ArrayList<LockedSection> lockedSectionsKeysConfig;
}
