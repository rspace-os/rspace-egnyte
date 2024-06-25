package com.researchspace.egnyte.api.model;

import java.util.Date;

import lombok.Data;

/**
 * Represents search result of file.
 */

@Data
public class EgnyteSearchedFile {

    private String name;
    private String path;
    private boolean isFolder = false;
    private Long size;
    /**
     * Snippet of file's content
     */
    private String snippet;
    /**
     * Version identifier of the file.
     */
    private String entryId;
    /**
     * Identifier of all file versions.
     */
    private String groupId;
    /**
     * Date of last file modification
     */
    private Date lastModified;
    /**
     * Full name of user who uploaded the file
     */
    private String uploadedBy;
    /**
     * Username of user who upladed the file
     */
    private String uploadedByUsername;
    /**
     * Number of file versions
     */
    private int numVersions;
}
