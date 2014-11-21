package com.android.test.otto;

import android.location.Location;

/**
 * Created by Nicolas Jafelle on 11/13/14.
 */
public class VenueResultEvent {

//    public List<Venue> venues;

    public String place;

    public Location location;


    public VenueResultEvent(String place, Location location) {
//        this.venues = venues;
        this.place = place;
        this.location = location;
    }

}
