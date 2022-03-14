package com.airtel.cs.model.cs.response.hbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HbbUserDetailsResult {
    private String firstName;
    private String userName;
    private String email;
    private List<AlternateMsisdn> alternateMsisdnList;
    private String gender;
    private Boolean isEmailVerified;

}
