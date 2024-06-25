package com.researchspace.egnyte.api.model;

import lombok.Data;

@Data
public class EgnyteSearchedEntry {
    private String name;
    private String path;
    private boolean isFolder;
}
