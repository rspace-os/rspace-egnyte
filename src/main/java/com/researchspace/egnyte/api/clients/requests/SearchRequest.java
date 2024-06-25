package com.researchspace.egnyte.api.clients.requests;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.researchspace.egnyte.api.model.EntryType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchRequest {
    
	@NotBlank
    @Size(min=3, max=100)
    private String query;

    // optional
    @Min(0)
    @Builder.Default
    private Integer offset=0;
    
    @Max(20)
    @Min(1)
    @Builder.Default
    private Integer count=20;
    private String folder;

    @JsonProperty("modified_before")
    private Long modifiedBefore;
    
    @JsonProperty("modified_after")
    private Long modifiedAfter;

    /**
     * Default is EntryType.FILE
     */
    @Builder.Default
    private EntryType type = EntryType.FILE;

    public void setModifiedBefore(Date date){
        this.modifiedBefore = date.getTime();
    }

    public void setModifiedAfter(Date date){
        this.modifiedAfter = date.getTime();
    }

 

}
