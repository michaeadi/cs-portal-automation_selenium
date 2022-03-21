package com.airtel.cs.commonutils.dataproviders.databeans;

import java.util.List;

import lombok.Data;

@Data
public class IssueFieldsDataBean {
    String issueFieldLabel;
    String issueFieldType;
    String issueFieldMandatory;
    String fieldName;
    List<Object> fieldValues;
    SearchType searchType;
}
