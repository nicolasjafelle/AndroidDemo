package com.android.test.dto;


/**
 * FoursquareDto
 * Created by nicolas on 12/22/13.
 */
public abstract class FoursquareDto {

	private Meta meta;
	private Response response;
	// for this test we don't need additional attributes like confident or geocode.


	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
