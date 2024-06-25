package com.researchspace.egnyte.api.model;

import com.researchspace.egnyte.api.clients.requests.JsonRequestBody;

import lombok.Data;

@Data
public class SearchRange extends JsonRequestBody {

    private Double start;
    private Double end;

}
