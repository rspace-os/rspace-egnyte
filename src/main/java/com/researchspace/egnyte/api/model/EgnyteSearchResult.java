package com.researchspace.egnyte.api.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.researchspace.egnyte.api.clients.responses.ResponseObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class EgnyteSearchResult extends ResponseObject {
    private Long count;

    @JsonProperty("total_count")
    private Long totalCount;
    private boolean hasMore;
    private Long offset;

    private List<SearchResultItem> results;

}
