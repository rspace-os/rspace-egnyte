package com.researchspace.egnyte.api.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FolderCreateResponse {
    @JsonProperty("folder_id")
    private String folderID;
}
