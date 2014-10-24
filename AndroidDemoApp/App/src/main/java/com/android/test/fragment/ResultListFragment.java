package com.android.test.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.adapter.VenueAdapter;
import com.android.test.client.FoursquareClient;
import com.android.test.dialog.DialogFragmentHelper;
import com.android.test.dialog.ProgressDialogFragment;
import com.android.test.dialog.VenueDialogFragment;
import com.android.test.domain.Venue;
import com.android.test.dto.ErrorType;
import com.android.test.dto.FoursquareApiErrorDto;
import com.android.test.dto.VenueDto;
import com.android.test.location.GPSTracker;
import com.android.test.task.FoursquareAsyncTask;
import com.android.test.utils.DataHelper;

import java.util.List;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

/**
 * MainFragment
 * Created by nicolas on 12/22/13.
 */
public class ResultListFragment extends AbstractFragment<ResultListFragment.Callback> implements AdapterView.OnItemClickListener {


    public interface Callback {
        void onItemClick(Venue venue);
    }

    @InjectExtra(value = "LISTA", optional = true)
    private DataHelper dataHelper;

    @InjectExtra(value = "LOCATION", optional = true)
    private Location currentLocation;

    @InjectView(R.id.fragment_result_list_listview)
	private ListView listView;

	private VenueDialogFragment venueDialogFragment;

	private VenueAdapter venueAdapter;


	public static Fragment newInstance() {
		return new ResultListFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        listView.setOnItemClickListener(this);

        venueDialogFragment = VenueDialogFragment.newInstance();

        venueAdapter = new VenueAdapter(dataHelper.getList(), currentLocation);
        listView.setAdapter(venueAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Venue venue = (Venue)parent.getItemAtPosition(position);
		createVenueDialog(venue);

//        callbacks.onItemClick(venue);

	}

	private void createProgressDialog(int resId) {
		Bundle arguments = new Bundle();
		arguments.putString(ProgressDialogFragment.MESSAGE, getString(resId));
	}

	private void createVenueDialog(Venue venue) {
		Bundle arguments = new Bundle();
		arguments.putSerializable(VenueDialogFragment.SELECTED_VENUE, venue);
		DialogFragmentHelper.show(getActivity(), venueDialogFragment, arguments);
	}








}
