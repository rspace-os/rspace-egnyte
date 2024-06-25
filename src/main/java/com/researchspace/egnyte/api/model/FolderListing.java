package com.researchspace.egnyte.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.researchspace.egnyte.api.clients.responses.ResponseObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FolderListing extends ResponseObject  {
    private String name;
    private Long lastModified;
    private String path;
    @JsonProperty("folder_id")
    private String folderId;
    @JsonProperty("parent_id")
    private String parent_id;
    @JsonProperty("is_folder")
    private boolean isFolder;


    private Long offset;
    @JsonProperty("total_count")
    private Long totalCount;
    @JsonProperty("restrict_move_delete")
    private String restrictMoveDelete;
    @JsonProperty("public_links")
    private String publicLinks;

   
}
