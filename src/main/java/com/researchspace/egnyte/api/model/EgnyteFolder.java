package com.researchspace.egnyte.api.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Represents Egnyte folder
 */
@Data
@RequiredArgsConstructor
public final class EgnyteFolder {

    /**
     * Name of the folder
     */
    public final String name;

    /**
     * Timestamp of last modification
     */
    public final long lastModified;

    /**
     * Path of the folder
     */
    public final String path;

    /**
     * Identifier of the folder
     */
    public final String folderId;

    /**
     * Always true
     */
    public final boolean isFolder;

}
