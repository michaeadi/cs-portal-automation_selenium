package com.airtel.cs.model.sr.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LayoutConfigRequest {
    private String layoutConfigType;
    private int categoryId;
}