package com.researchspace.egnyte.api.clients.requests;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriUtils;

import lombok.Data;

@Data
public abstract class RequestBody {

    public abstract MediaType format();

    public abstract boolean isStreamed();


    protected String encodeValue(String value) throws UnsupportedEncodingException {
        return UriUtils.encodeQueryParam(value, StandardCharsets.UTF_8.toString());
    }

 

}

