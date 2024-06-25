package com.researchspace.egnyte.api.model;

import com.researchspace.egnyte.api.clients.responses.ResponseObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomProperty extends ResponseObject {
 private String scope, namespace, key, value;
}
