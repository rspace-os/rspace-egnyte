package com.researchspace.egnyte.api.model;

import java.util.List;

import com.researchspace.egnyte.api.clients.requests.JsonRequestBody;

import lombok.Data;

@Data
public class SearchMetadata extends JsonRequestBody {

    /**
     * required
     * */
    private String namespace;
    private String key;
    private SearchOperator operator = SearchOperator.EQUALS;

    /**
     * optional
     * */

    // Value for operator EQUALS, GREATER_THAN, LESS_THAN, CONTAINS
    private String value;
    //List of values for operator IN
    private List<String> values;
    private SearchRange range;


    public SearchMetadata(String namespace, String key, SearchOperator operator){
        setNamespace(namespace);
        setKey(key);
        setOperator(operator);
    }

    public SearchMetadata(String namespace, String key){
        this(namespace, key, SearchOperator.EQUALS);
    }

}
