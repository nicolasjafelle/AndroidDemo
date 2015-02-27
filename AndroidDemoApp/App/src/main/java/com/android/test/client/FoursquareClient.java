package com.android.test.client;

import com.android.test.dto.VenueDto;
import com.google.inject.Singleton;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * This class is the responsable to connect to the API. Here lies all the
 * connections between the client and the server.<br>
 * As <b>Jake Wharton</b> said:
 * "RestAdapter and the created instances should be treated as singletons"
 * @author nicolasjafelle
 */
@Singleton
public class FoursquareClient {

	private static final String BASE_URL = "https://api.foursquare.com/v2";

	private RestAdapter restAdapter;
	private IFoursquareClient foursquareClient;

	public FoursquareClient() {
		if (restAdapter == null || foursquareClient == null) {

            OkHttpClient okHttpClient = new OkHttpClient();

			restAdapter = new RestAdapter.Builder()
				.setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.build();

			foursquareClient = restAdapter.create(IFoursquareClient.class);
		}
	}


	public VenueDto searchForVenues(String criteria) {
		String token = "HDQVML4JWDJNHHKCSRPN0MPYUFEMJJEXH3C34FTCPYUOR5OP";
		long v = 20130417;

		return foursquareClient.searchForVenues(criteria, token, v);
	}


}
