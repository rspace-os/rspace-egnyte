package com.researchspace.egnyte.api.model;

import java.text.SimpleDateFormat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class EgnyteFile{

    private static final SimpleDateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

    /**
     * SHA512 checksum of the file
     */
    public final String checksum;
    /**
     * Size in bytes of the file
     */
    public final long size;
    /**
     * Path of the file
     */
    public final String path;
    /**
     * Name of the file
     */
    public final String name;
    /**
     * Describes if file is locked for modificaiton.
     */
    public final boolean locked;
    /**
     * Alsways false
     */
    public final boolean isFolder;
    /**
     * Version identifier of the file.
     */
    public final String entryId;
    /**
     * Identifier of all file versions.
     */
    public final String groupId;
    /**
     * Date of last file modification
     */
    public final long lastModified;
    /**
     * Uploader of the file.
     */
    public final String uploadedBy;
    /**
     * Number of the flie versions.
     */
    public final int numVersions;
}
