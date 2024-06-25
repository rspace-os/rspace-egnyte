package com.researchspace.egnyte.api.clients.requests;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * A basic file upload request, requiring only the File to upload and the desired path
 */
@Data
public class SimpleFileUploadRequest {
    @NotNull
	private File file;
    @NotBlank
	private String path;
    public SimpleFileUploadRequest(String path, File file)  {
       this.file = file;
       this.path = path;
    }

 
}
