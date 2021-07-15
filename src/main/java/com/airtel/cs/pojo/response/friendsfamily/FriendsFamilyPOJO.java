package com.airtel.cs.pojo.response.friendsfamily;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendsFamilyPOJO {
    private String message;
    private String status;
    private int statusCode;
    private ArrayList<FriendsFamilyInformation> result;
}
