package com.researchspace.egnyte.api.clients.requests;


import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;

import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class JsonRequestBody extends RequestBody {

    @Override
    public MediaType format() {
        return MediaType.APPLICATION_JSON_UTF8;
    }

    @Override
    public boolean isStreamed() {
        return false;
    }
}
