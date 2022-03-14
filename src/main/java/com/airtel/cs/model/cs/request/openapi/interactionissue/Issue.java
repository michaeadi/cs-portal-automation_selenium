package com.airtel.cs.model.cs.request.openapi.interactionissue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.interactions.Interaction;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Issue implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = -4420679330184836534L;


    private Long issueId;

    private List<Interaction> interaction;

    private List<IssueCategoryMappingConfiguration> categoryHierarchy;

    private List<Object> issueDetails;

    private String comment;

    private Boolean actionPerformed;

    private String createdBy;

    private Timestamp createdOn;

    private String updatedBy;

    private Timestamp updatedOn;


}
