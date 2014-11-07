package com.android.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.view.ObservableScrollView;
import com.squareup.picasso.Picasso;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;


public class DetailFragment extends AbstractFragment<DetailFragment.Callback> {

    public static final String ANIMATED_IMAGE = "image";
    public static final String ANIMATED_NAME = "name";
    public static final String ANIMATED_DISTANCE = "distance";

    public interface Callback {
        void onSetupFadingActionBar(ObservableScrollView observableScrollView, final View header);
    }

    @InjectView(R.id.fragment_detail_observable_scroll_view)
    private ObservableScrollView scrollview;

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

        callbacks.onSetupFadingActionBar(scrollview, imageView);

        ViewCompat.setTransitionName(imageView, ANIMATED_IMAGE);
        ViewCompat.setTransitionName(nameView, ANIMATED_NAME);
        ViewCompat.setTransitionName(distanceView, ANIMATED_DISTANCE);

        Picasso.with(getActivity())
                .load(url)
                .into(imageView);

        nameView.setText(name);
        distanceView.setText(distance);

	}


}
