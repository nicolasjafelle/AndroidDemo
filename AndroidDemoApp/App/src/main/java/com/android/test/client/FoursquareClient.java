package com.android.test.client;

import com.android.test.dto.VenueDto;

import retrofit.RestAdapter;

/**
 * This class is the responsable to connect to the API. Here lies all the
 * connections between the client and the server.<br>
 * As <b>Jake Wharton</b> said:
 * "RestAdapter and the created instances should be treated as singletons"
 * @author nicolasjafelle
 */
public class FoursquareClient {

	private static final String BASE_URL = "https://api.foursquare.com/v2";

	private RestAdapter restAdapter;
	private IFoursquareClient foursquareClient;

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		public static final FoursquareClient instance = new FoursquareClient();
	}

	public static FoursquareClient getInstance() {
		return SingletonHolder.instance;
	}

	private FoursquareClient() {
		if (restAdapter == null || foursquareClient == null) {

			restAdapter = new RestAdapter.Builder()
				.setEndpoint(BASE_URL)
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
