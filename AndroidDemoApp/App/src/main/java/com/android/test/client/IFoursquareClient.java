package com.android.test.client;

import com.android.test.dto.VenueDto;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by nicolas on 12/22/13.
 */
public interface IFoursquareClient {

	@GET("/venues/search")
	VenueDto searchForVenues(@Query("near") String place, @Query("oauth_token") String token, @Query("v") long v);
}
