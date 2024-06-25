package com.researchspace.egnyte.api.model;

import java.io.File;

import lombok.Data;

@Data
public class FileDownloadResult {
	
	public FileDownloadResult(File file) {
		this.downloaded = file;
	}

	private File downloaded;

}
