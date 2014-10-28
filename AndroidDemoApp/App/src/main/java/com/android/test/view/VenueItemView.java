package com.android.test.view;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.domain.Venue;
import com.android.test.utils.ViewUtil;
import com.bumptech.glide.Glide;

import java.util.Random;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * VenueItemView
 * Created by nicolas on 12/22/13.
 */
public class VenueItemView extends RelativeLayout {

    private String RANDOM_IMAGE_URL = "http://lorempixel.com/640/480/abstract/";

    @InjectView(R.id.venue_item_name)
	private TextView nameView;

    @InjectView(R.id.venue_item_distance)
	private TextView distanceView;

    @InjectView(R.id.venue_item_abstract_image)
    private ImageView randomImage;

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
        ViewUtil.reallyInjectViews(this);

        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((10 - 1) + 1) + 1;
        Log.i("RANDOMNUMBER", "Random number = " + randomNum);

        RANDOM_IMAGE_URL = RANDOM_IMAGE_URL + randomNum;


	}

	public void fillData(Venue venue, Location currentLocation) {



		nameView.setText(venue.getName());

        Glide.with(getContext())
                .load(RANDOM_IMAGE_URL)
                .centerCrop()
                .crossFade()
                .into(randomImage);

		if(currentLocation != null) {
			float[] results = new float[3];
			Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
				venue.getLocation().getLat(), venue.getLocation().getLng(), results);

			double km = results[0] / 1000;
			distanceView.setText(String.format("%.2f Km", km));
		}
	}
}
