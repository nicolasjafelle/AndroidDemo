package com.android.test.dto;

import com.android.test.domain.Venue;

import java.util.List;

/**
 * Created by nicolas on 12/22/13.
 */
public class Response {

	private List<Venue> venues;

	public List<Venue> getVenues() {
		return venues;
	}

	public void setVenues(List<Venue> venues) {
		this.venues = venues;
	}
}
