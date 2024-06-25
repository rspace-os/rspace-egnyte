package com.researchspace.egnyte.api.clients.requests;

import com.researchspace.egnyte.api2.EgnyteException;

/**
 * Parses error in failed requests
 * */
public interface ConnectionErrorParser {
    EgnyteException parse(Exception e);
}
