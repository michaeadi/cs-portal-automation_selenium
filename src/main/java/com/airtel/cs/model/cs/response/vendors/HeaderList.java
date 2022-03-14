package com.airtel.cs.model.cs.response.vendors;

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
public class HeaderList {
    private String header;
    private String key;
    private String columnWidth;
    private String color;
    private String subHeader;
    private String dateFormat;
    private String timeFormat;
    private Double value;
}
