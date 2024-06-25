package com.researchspace.egnyte.api.model;

public enum  EntryType {
    FOLDER{
        @Override
        public String toString() {
            return "FOLDER";
        }
    }, FILE{
        @Override
        public String toString() {
            return "FILE";
        }
    }, ALL{
        @Override
        public String toString() {
            return "ALL";
        }
    }
}
