package com.researchspace.egnyte.api.model;

import java.io.File;

import com.researchspace.egnyte.api.clients.responses.ResponseObject;

import lombok.Data;

@Data
public class ChunkedUploadResult extends ResponseObject {

    /**
     * Cloud path to upload to
     */
    public final String cloudPath;

    /**
     * File to upload
     */
    public final File sourceFile;

    /**
     * Chunk Size
     */
    public final long chunkSize;

    /**
     * Upload id. Null for result of uploading last chunk.
     */
    public final String uploadId;

    /**
     * Chunk number, 1-indexed. Null for result of uploading last chunk.
     */
    public final Integer chunkNum;

    /**
     * SHA512 hash of entire file. Might be null.
     */
    public final String checksum;
}
