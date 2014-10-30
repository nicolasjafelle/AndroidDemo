package com.android.test.adapter;

import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.test.domain.Venue;
import com.android.test.view.VenueItemView;

import java.util.List;

/**
 * VenueAdapter
 * Created by nicolas on 12/22/13.
 */
@Deprecated
public class VenueAdapter extends BaseAdapter {

	private List<Venue> venues;
	private Location currentLocation;

	public VenueAdapter(List<Venue> venues) {
		this.venues = venues;
	}

	public VenueAdapter(List<Venue> venues, Location currentLocation) {
		this.venues = venues;
		this.currentLocation = currentLocation;
	}

	@Override
	public int getCount() {
		return venues.size();
	}

	@Override
	public Object getItem(int position) {
		return venues.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VenueItemView view;
		Venue venue = venues.get(position);

		if(convertView != null) {
			view = (VenueItemView) convertView;
		}else{
			view = new VenueItemView(parent.getContext());
		}



		view.fillData(venue, currentLocation);
		return view;
	}
}
