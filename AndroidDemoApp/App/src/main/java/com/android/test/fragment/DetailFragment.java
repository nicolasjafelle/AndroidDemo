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


public class DetailFragment extends AbstractFragment<DetailFragment.Callback> {

    public static final String ANIMATED_IMAGE = "image";
    public static final String ANIMATED_NAME = "name";
    public static final String ANIMATED_DISTANCE = "distance";

    public interface Callback {
        void onSetupFadingActionBar(ObservableScrollView observableScrollView, final View header);
    }

    private ObservableScrollView scrollview;

    private ImageView imageView;

    private TextView nameView;

    private TextView distanceView;

    private String url;

    private String name;

    private String distance;


	public static Fragment newInstance(String url, String name, String distance) {
        Bundle args = new Bundle();
        args.putString(DetailFragment.ANIMATED_IMAGE, url);
        args.putString(DetailFragment.ANIMATED_NAME, name);
        args.putString(DetailFragment.ANIMATED_DISTANCE, distance);

        Fragment f = new DetailFragment();
        f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        scrollview = (ObservableScrollView) view.findViewById(R.id.fragment_detail_observable_scroll_view);
        imageView = (ImageView) view.findViewById(R.id.fragment_detail_image_view);
        nameView = (TextView) view.findViewById(R.id.fragment_detail_name);
        distanceView = (TextView) view.findViewById(R.id.fragment_detail_distance);

        return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            url = getArguments().getString(ANIMATED_IMAGE);
            name = getArguments().getString(ANIMATED_NAME);
            distance = getArguments().getString(ANIMATED_DISTANCE);
        }


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
