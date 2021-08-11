package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigurationRequest {
    private Integer pageNumber;
    private Integer pageSize;
}
