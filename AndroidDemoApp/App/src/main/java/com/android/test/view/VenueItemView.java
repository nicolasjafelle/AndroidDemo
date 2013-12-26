package com.android.test.view;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.domain.Venue;

/**
 * VenueItemView
 * Created by nicolas on 12/22/13.
 */
public class VenueItemView extends LinearLayout {

	private TextView nameView;
	private TextView distanceView;

	public VenueItemView(Context context) {
		super(context);
		init();
	}

	public VenueItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public VenueItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.venue_item, this);
		nameView = (TextView) findViewById(R.id.venue_item_name);
		distanceView = (TextView) findViewById(R.id.venue_item_distance);

	}

	public void fillData(Venue venue, Location currentLocation) {
		nameView.setText(venue.getName());

		if(currentLocation != null) {
			float[] results = new float[3];
			Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
				venue.getLocation().getLat(), venue.getLocation().getLng(), results);

			double km = results[0] / 1000;
			distanceView.setText(String.format("%.2f Km", km));
		}
	}
}
