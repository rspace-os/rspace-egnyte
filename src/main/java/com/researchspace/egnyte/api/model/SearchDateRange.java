package com.researchspace.egnyte.api.model;

import java.sql.Date;

import lombok.Data;


@Data
public class SearchDateRange extends SearchRange {

    public void setStart(Date start) {
        super.setStart((double) start.getTime());
    }

    public void setEnd(Date end) {
        super.setEnd((double) end.getTime());
    }
}
