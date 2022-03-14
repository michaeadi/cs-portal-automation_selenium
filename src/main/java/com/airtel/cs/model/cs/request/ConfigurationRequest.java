package com.airtel.cs.model.cs.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigurationRequest {
    private Integer pageNumber;
    private Integer pageSize;
}
