package com.android.test.qachee;

import com.android.test.domain.Venue;
import com.qachee.QacheeableObject;

import java.util.List;

/**
 * Created by Nicolas Jafelle on 11/21/14.
 */
public class QacheeData extends QacheeableObject {

    public String search;

    public List<Venue> venues;


    public QacheeData(String search, List<Venue> venues) {
        this.search = search;
        this.venues = venues;
    }

    @Override
    public Long getKey() {
        return (long) this.search.hashCode();
    }
}
