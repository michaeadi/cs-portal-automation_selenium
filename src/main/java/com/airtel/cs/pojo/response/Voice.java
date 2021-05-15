package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.ALWAYS)

public class Voice {
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    String balance;
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    long expireTime;
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    ArrayList<Accounts> accounts;
}
