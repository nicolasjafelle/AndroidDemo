package com.android.test.task;

import android.location.Location;

import com.android.test.client.FoursquareClient;
import com.android.test.domain.Venue;
import com.android.test.dto.FoursquareApiErrorDto;
import com.android.test.dto.VenueDto;
import com.android.test.otto.OttoBus;
import com.android.test.otto.VenueResultEvent;
import com.android.test.task.event.OnApiErrorEvent;
import com.android.test.task.event.OnFinallyEvent;
import com.android.test.task.event.OnPreExecuteEvent;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Nicolas Jafelle on 11/28/14.
 */
public class VenueBackgroundTask extends FoursquareAsyncTask<VenueDto> {

    @Inject
    private OttoBus ottoBus;

    @Inject
    private FoursquareClient foursquareClient;

    private String criteria;
    private Location currentLocation;

    public VenueBackgroundTask(String criteria, Location currentLocation) {
        super();
        this.criteria = criteria;
        this.currentLocation = currentLocation;
    }

    @Override
    protected void onPreExecute() throws Exception {
        super.onPreExecute();
        ottoBus.post(new OnPreExecuteEvent());
    }

    @Override
    public VenueDto call() throws Exception {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        return foursquareClient.searchForVenues(this.criteria);
    }

    @Override
    protected void onSuccess(VenueDto venueDto) throws Exception {
        super.onSuccess(venueDto);

        if(!isCancelled()) {
            List<Venue> venues = venueDto.getResponse().getVenues();
            ottoBus.post(new VenueResultEvent(venues, criteria, currentLocation));
        }
    }

    @Override
    protected void onApiError(FoursquareApiErrorDto errorDto) {
        ottoBus.post(new OnApiErrorEvent(errorDto));
    }

    @Override
    protected void onFinally() throws RuntimeException {
        super.onFinally();
        super.clear();
        ottoBus.post(new OnFinallyEvent());
    }
}
