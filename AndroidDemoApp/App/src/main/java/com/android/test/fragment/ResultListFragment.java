package com.android.test.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.adapter.VenueRecyclerAdapter;
import com.android.test.domain.Venue;
import com.android.test.session.SessionManager;
import com.android.test.utils.DataHelper;
import com.melnykov.fab.FloatingActionButton;

import javax.inject.Inject;


public class ResultListFragment extends AbstractFragment<ResultListFragment.Callback>
        implements VenueRecyclerAdapter.RecyclerViewListener {

    public static final String DATA_HELPER = "data_helper";
    public static final String PLACE = "place";
    public static final String LOCATION = "location";

    public interface Callback {
        void onItemClick(Venue venue, View view, String url);
        void onToolbarHide();
        void onToolbarShow();
    }

    private DataHelper dataHelper;

    private String place;

    private Location currentLocation;

    private FloatingActionButton fab;

    private RecyclerView recyclerView;

    @Inject
    private SessionManager sessionManager;


    private VenueRecyclerAdapter adapter;


	public static Fragment newInstance(DataHelper dataHelper, String place, Location currentLocation) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_HELPER, dataHelper);
        bundle.putString(PLACE, place);
        bundle.putParcelable(LOCATION, currentLocation);

        Fragment f = new ResultListFragment();
        f.setArguments(bundle);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_list, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_result_list_floating_button);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_result_list_recyclerview);

        return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            dataHelper = (DataHelper) getArguments().getSerializable(DATA_HELPER);
            place = getArguments().getString(PLACE);
            currentLocation = getArguments().getParcelable(LOCATION);
        }

        setupRecyclerView();
        setupFab();
	}

    private void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.savePlace(place);

                Toast.makeText(getActivity(), getString(R.string.location_saved_open_the_side_bar_to_see_it, place),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);

//        use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new VenueRecyclerAdapter(dataHelper.getList(), currentLocation, this);
        recyclerView.setAdapter(adapter);


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isShowing = true;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    if (isShowing) {
                        callbacks.onToolbarHide();
                        fab.hide();
                        isShowing = false;
                    }
                } else {
                    if (!isShowing) {
                        callbacks.onToolbarShow();
                        fab.show();
                        isShowing = true;
                    }
                }
            }
        });
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Venue venue = adapter.getItemAtPosition(position);
        String url = (String) view.getTag();

        callbacks.onItemClick(venue, view, url);
    }

}
