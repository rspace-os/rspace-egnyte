package com.researchspace.egnyte.api.clients.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteRequest {	
	

	private String pathToDelete;
	
	public DeleteRequest(String pathToDelete) {
		super();
		this.pathToDelete = pathToDelete;
	}

}
