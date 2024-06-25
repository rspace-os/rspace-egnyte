package com.researchspace.egnyte.api.model;

public enum FilePermissions {
    KEEP_ORIGINAL{
        @Override
        public String toString() {
            return "keep_original";
        }
    }, INHERIT_FROM_PARENT{
        @Override
        public String toString() {
            return "inherit_from_parent";
        }
    }
}
