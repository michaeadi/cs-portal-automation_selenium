package com.airtel.cs.model.sr.request.ticketsearch;
import java.util.List;


import com.airtel.cs.commonutils.dataproviders.databeans.SearchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueFields {
    String issueFieldLabel;
    String issueFieldType;
    String issueFieldMandatory;
    String fieldName;
    List<Object> fieldValues;
    SearchType searchType;
}
