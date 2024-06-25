package com.researchspace.egnyte.api.model;

public enum SearchOperator {
    EQUALS{
        @Override
        public String toString() {
            return "EQUALS";
        }
    }, GREATER_THAN {
        @Override
        public String toString() {
            return "GREATER_THAN";
        }
    }, LESS_THAN {
        @Override
        public String toString() {
            return "LESS_THAN";
        }
    }, CONTAINS {
        @Override
        public String toString() {
            return "CONTAINS";
        }
    }, IN {
        @Override
        public String toString() {
            return "IN";
        }
    }, BETWEEN {
        @Override
        public String toString() {
            return "BETWEEN";
        }
    }
}
