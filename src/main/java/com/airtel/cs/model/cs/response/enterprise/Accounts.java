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
public class Accounts {
    public String accountName;
    public String accountNo;
    public String accountType;
    public String billingAcountId;
    public String parentAccountNo;
    public String parentAccountName;
    public long personId;
    public String contactPersonfirstName;
    public String contactPersonlastName;
    public String contactPersonMsisdn;
    public String contactPersonEmailId;
    public Kam kam;
    public String segment;
    public String subSegment;
    public String customerCategory;
    public CustomerAddress customerAddress;
    public long activationDate;
    public String vipFlag;
    public long modifiedDate;
    public boolean invoicedAccount;
}
