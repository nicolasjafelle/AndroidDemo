package com.android.test.adapter;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.test.domain.Venue;
import com.android.test.view.VenueItemView;

import java.util.List;

/**
 * Created by Nicolas Jafelle on 10/28/14.
 */
public class VenueRecyclerAdapter extends RecyclerView.Adapter<VenueRecyclerAdapter.ViewHolder> {

    private enum ViewTypes {
        FIRST_VIEW,
        REST_VIEW;
    }

    public interface RecyclerViewListener {
        void onItemClickListener(View view , int position);

    }

    private List<Venue> venues;
    private Location currentLocation;
    private RecyclerViewListener recyclerViewListener;

    public VenueRecyclerAdapter(List<Venue> venues, Location currentLocation, RecyclerViewListener recyclerViewListener) {
        this.venues = venues;
        this.currentLocation = currentLocation;
        this.recyclerViewListener = recyclerViewListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // create a new view
        VenueItemView view = new VenueItemView(viewGroup.getContext());
        // set the view's size, margins, paddings and layout parameters
        if(viewType == ViewTypes.FIRST_VIEW.ordinal()) {
            view.setFirst();
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Venue rowData = venues.get(position);
        viewHolder.venueItemView.fillData(rowData, currentLocation);
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return ViewTypes.FIRST_VIEW.ordinal();
        }else {
            return ViewTypes.REST_VIEW.ordinal();
        }

    }

    public Venue getItemAtPosition(int position) {
        return venues.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final VenueItemView venueItemView;

        public ViewHolder(VenueItemView itemView) {
            super(itemView);
            venueItemView = itemView;
            venueItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewListener.onItemClickListener(v, getPosition());
        }
    }
}
