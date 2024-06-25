package com.researchspace.egnyte.api.clients.requests;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.researchspace.egnyte.api.model.FilePermissions;

import lombok.Data;
/**
 * Default permissions is 'INHERIT_FROM_PARENT'
 */
@Data
public class MoveRequest {
	@JsonIgnore
	@NotBlank
	private String source;
	@NotBlank
	private String destination;
	
	/**
	 * Destination target of Move
	 * @param source
	 * @param destination
	 */
	public MoveRequest(String source, String destination) {
		super();
		this.source=source;
		this.destination = destination;
	}
	private String action = "move";
	private FilePermissions permissions = FilePermissions.KEEP_ORIGINAL;

}
