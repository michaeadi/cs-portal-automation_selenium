package com.airtel.cs.model.cs.response.enterprise;
import com.airtel.cs.model.cs.response.customeprofile.CustomerAddress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Kam {
    public String name;
    public String auuid;
    public String phoneNumber;
    public String emailID;
}
