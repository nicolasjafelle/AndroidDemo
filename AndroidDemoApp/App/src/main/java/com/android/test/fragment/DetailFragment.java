package com.android.test.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.adapter.VenueRecyclerAdapter;
import com.android.test.dialog.DialogFragmentHelper;
import com.android.test.dialog.VenueDialogFragment;
import com.android.test.domain.Venue;
import com.android.test.session.SessionManager;
import com.android.test.utils.DataHelper;
import com.bumptech.glide.Glide;
import com.melnykov.fab.FloatingActionButton;

import javax.inject.Inject;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;


public class DetailFragment extends AbstractFragment<DetailFragment.Callback> {

    public static final String ANIMATED_IMAGE = "image";
    public static final String ANIMATED_NAME = "name";
    public static final String ANIMATED_DISTANCE = "distance";

    public interface Callback {
        //TODO
    }

    @InjectView(R.id.fragment_detail_image_view)
    private ImageView imageView;

    @InjectView(R.id.fragment_detail_name)
    private TextView nameView;

    @InjectView(R.id.fragment_detail_distance)
    private TextView distanceView;

    @InjectExtra(value = ANIMATED_IMAGE, optional = false)
    private String url;

    @InjectExtra(value = ANIMATED_NAME, optional = false)
    private String name;

    @InjectExtra(value = ANIMATED_DISTANCE, optional = false)
    private String distance;


	public static Fragment newInstance() {
		return new DetailFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        ViewCompat.setTransitionName(imageView, ANIMATED_IMAGE);
        ViewCompat.setTransitionName(nameView, ANIMATED_NAME);
        ViewCompat.setTransitionName(distanceView, ANIMATED_DISTANCE);

        Glide.with(getActivity())
                .load(url)
                .crossFade()
                .into(imageView);

        nameView.setText(name);
        distanceView.setText(distance);

	}











}
