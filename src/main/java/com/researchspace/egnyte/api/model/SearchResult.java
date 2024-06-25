package com.researchspace.egnyte.api.model;


import java.util.ArrayList;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This class represents search result
 * {@link EgnyteSearchedFolder} or {@link EgnyteSearchedFile}
 */

@Data
@RequiredArgsConstructor
public class SearchResult<T> {

    /**
     * Number of results
     */
    public final int count;
    /**
     * The zero-based index from which results were returned
     */
    public final int offset;
    /**
     * Results of search query
     */
    public final ArrayList<T> result;
    /**
     * Whether there are more results
     */
    public final boolean hasMore;
    /**
     * Total count of results for given query
     */
    public final int totalCount;
}
