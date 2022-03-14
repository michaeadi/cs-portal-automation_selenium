package com.airtel.cs.model.sr.request.layout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class V2LayoutRequest {
    private Integer categoryId;
    private String layoutConfigType;
    private String actionKey;

    public V2LayoutRequest(Integer categoryId, String layoutConfigType){
        this.categoryId = categoryId;
        this.layoutConfigType = layoutConfigType;
    }
}
