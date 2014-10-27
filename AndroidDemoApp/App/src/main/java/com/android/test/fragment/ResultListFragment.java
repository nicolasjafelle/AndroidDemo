package com.android.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.test.R;
import com.android.test.adapter.VenueAdapter;
import com.android.test.dialog.DialogFragmentHelper;
import com.android.test.dialog.VenueDialogFragment;
import com.android.test.domain.Venue;
import com.android.test.utils.DataHelper;
import com.android.test.view.OverlayView;
import com.melnykov.fab.FloatingActionButton;
import android.location.Location;
import android.widget.Toast;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;


public class ResultListFragment extends AbstractFragment<ResultListFragment.Callback>
        implements AdapterView.OnItemClickListener {


    public interface Callback {
        void onItemClick(Venue venue);
        void onToolbarHide();
        void onToolbarShow();
    }

    @InjectExtra(value = "LISTA", optional = true)
    private DataHelper dataHelper;

    @InjectExtra(value = "LOCATION", optional = true)
    private Location currentLocation;

    @InjectView(R.id.fragment_result_list_listview)
	private ListView listView;

    @InjectView(R.id.fragment_result_list_floating_button)
    private FloatingActionButton fab;

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



        venueDialogFragment = VenueDialogFragment.newInstance();

        setupListView();
        setupFab();

	}

    private void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Work in Progress", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListView() {
        listView.setOnItemClickListener(this);

        OverlayView overlayView = new OverlayView(getActivity());
        listView.addHeaderView(overlayView, null, false);

        venueAdapter = new VenueAdapter(dataHelper.getList(), currentLocation);
        listView.setAdapter(venueAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lastFirstVisibleItem = 0;
            boolean isShowing = true;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Do nothing...
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lastFirstVisibleItem < firstVisibleItem) {
                    if(isShowing) {
                        callbacks.onToolbarHide();
                        fab.hide();
                        isShowing = false;
                    }
                }

                if (lastFirstVisibleItem > firstVisibleItem) {
                    if(!isShowing) {
                        callbacks.onToolbarShow();
                        fab.show();
                        isShowing = true;
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Venue venue = (Venue)parent.getItemAtPosition(position);
		createVenueDialog(venue);

//        callbacks.onItemClick(venue);

	}

	private void createVenueDialog(Venue venue) {
		Bundle arguments = new Bundle();
		arguments.putSerializable(VenueDialogFragment.SELECTED_VENUE, venue);
		DialogFragmentHelper.show(getActivity(), venueDialogFragment, arguments);
	}








}
