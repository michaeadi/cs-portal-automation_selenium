package com.airtel.cs.commonutils.dataproviders.databeans;

import java.util.List;

import lombok.Data;

@Data
public class IssueFields {
    String issueFieldLabel;
    String issueFieldType;
    String issueFieldMandatory;
    String fieldName;
    List<Object> fieldValues;
    SearchType searchType;
}
