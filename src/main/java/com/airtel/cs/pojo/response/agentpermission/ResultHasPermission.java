package com.airtel.cs.pojo.response.agentpermission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultHasPermission {
    public Boolean hasTransferToQueuePermission;
}
