package com.android.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.domain.Venue;
import com.android.test.utils.ViewUtil;
import com.squareup.picasso.Picasso;

import java.util.Random;

import roboguice.inject.InjectView;

/**
 * VenueItemView
 * Created by nicolas on 12/22/13.
 */
public class VenueItemView extends RelativeLayout {

    private String RANDOM_IMAGE_URL = "http://lorempixel.com/600/480/abstract/";

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
        RANDOM_IMAGE_URL = RANDOM_IMAGE_URL + randomNum;
	}

	public void fillData(Venue venue, Location currentLocation) {


        setTag(RANDOM_IMAGE_URL);
		nameView.setText(venue.getName());

        Picasso.with(getContext())
                .load(RANDOM_IMAGE_URL)
                .into(randomImage);

		if(currentLocation != null) {
			float[] results = new float[3];
			Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
				venue.getLocation().getLat(), venue.getLocation().getLng(), results);

			double km = results[0] / 1000;
            String distance = String.format("%.2f Km", km);

			distanceView.setText(distance);
		}
	}

    public String getDistanceText() {
        return this.distanceView.getText().toString();
    }

    public String getNameText() {
        return this.nameView.getText().toString();
    }

    public void setFirst() {

        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });

        float height;
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            height = styledAttributes.getDimension(0, 0);
        }else {
            height = styledAttributes.getDimension(0, 0) + getContext().getResources().getDimension(R.dimen.main_separation);
        }

        this.setPadding(0, (int)height, 0, 0);
        styledAttributes.recycle();
    }
}
