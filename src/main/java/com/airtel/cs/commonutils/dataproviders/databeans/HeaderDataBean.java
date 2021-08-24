package com.airtel.cs.commonutils.dataproviders.databeans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HeaderDataBean {

    private String tableName;
    private List<String> headerName;
}
