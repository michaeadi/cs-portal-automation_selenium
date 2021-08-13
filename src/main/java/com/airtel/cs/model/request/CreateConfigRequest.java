package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateConfigRequest {
    List<CreateConfigAttributes> createConfig;
}
