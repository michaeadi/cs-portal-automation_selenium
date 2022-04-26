package com.airtel.cs.model.cs.response.enterprise;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountsResponse {
    public ArrayList<Accounts> accounts;
    public int limit;
    public int offset;
    public int totalRecords;
}
