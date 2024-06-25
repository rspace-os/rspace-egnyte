package com.researchspace.egnyte.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.researchspace.egnyte.api.clients.responses.ResponseObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@RequiredArgsConstructor
public class FileUploadResult extends ResponseObject {
    /**
     * SHA512 hash of uploaded file
     */
    private String checksum;
    /**
     * EntryId identifying version of uploaded file
     */
    @JsonProperty(value="entry_id")
    private String entryId;
    /**
     * GroupId identifying uploaded file
     */
    @JsonProperty(value="group_id")
    private String groupId;

}
