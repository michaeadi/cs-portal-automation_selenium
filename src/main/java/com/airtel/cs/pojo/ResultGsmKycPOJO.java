package com.airtel.cs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultGsmKycPOJO {
    String name;
    long dob;
    String identificationType;
    String identificationNo;
    String gsm;
}
