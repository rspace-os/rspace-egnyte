package com.researchspace.egnyte.api.clients.requests;


import org.springframework.http.MediaType;

import lombok.Data;

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
