package com.android.test.utils;

import com.android.test.domain.Venue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas Jafelle on 10/24/14.
 */
public class DataHelper implements Serializable {

    private List<Venue> floors;

    public DataHelper(List<Venue> floors) {
        this.floors = floors;
    }

    public List<Venue> getList() {
        return this.floors;
    }
}
