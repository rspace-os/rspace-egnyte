package com.researchspace.egnyte.api.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.researchspace.egnyte.api.clients.responses.ResponseObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchResultItem extends ResponseObject {
	
	@JsonProperty("uploaded_by_username")
	private String uploadedByUsername;
	
	@JsonProperty("last_modified")
	Date lastModified;
	@JsonProperty("entry_id")
	String entryId;
	
	@JsonProperty("is_folder")
	boolean isFolder;
	@JsonProperty("uploaded_by")
	private String uploadedBy;
	private String path;
	private String name;
	
	@JsonProperty("group_id")
	private String groupId;
	
	private String snippet;
	@JsonProperty("snippet_html")
	private String snippetHtml;
	private Long size;
	
	@JsonProperty("custom_properties")
	private List<CustomProperty> customProperties;

}
