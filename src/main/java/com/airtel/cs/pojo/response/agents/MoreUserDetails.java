package com.airtel.cs.pojo.response.agents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoreUserDetails {
    private Integer id;
    private Integer auuid;
    private String msisdn;
    private Boolean active;
    private String firstName;
    private String lastName;
    private Integer totalCount;
    private ArrayList<RoleDetails> role;
    private ArrayList<Authorities> authorities;
}
