package com.airtel.cs.model.cs.response.loansummary;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Summary {
    private String vendorName;
    private String loanAmount;
    private Long creditedOn;
    private Long dueDate;
    private OutStanding currentOutstanding;

}
