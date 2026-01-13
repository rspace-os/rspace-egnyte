package com.researchspace.egnyte.api.model;

import com.researchspace.egnyte.api.clients.requests.JsonRequestBody;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRange extends JsonRequestBody {

    private Double start;
    private Double end;

}
