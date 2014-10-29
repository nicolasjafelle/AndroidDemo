package com.android.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.android.test.R;
import com.android.test.domain.Venue;
import com.android.test.fragment.DetailFragment;
import com.android.test.fragment.ResultListFragment;
import com.android.test.view.VenueItemView;
import com.bumptech.glide.Glide;

import roboguice.inject.InjectView;

public class ResultListActivity extends AbstractActionBarActivity implements ResultListFragment.Callback {

    private final int ANIM_DURATION = 200;

    @InjectView(R.id.material_toolbar)
    private Toolbar toolbar;

    private Interpolator mInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInterpolator = new AccelerateDecelerateInterpolator();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setInitialFragment() {
        setInitialFragment(R.layout.activity_main_overlay, R.id.container, ResultListFragment.newInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, SideBarActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(Venue venue, View view, String url) {
        DetailActivity.startActivity(this, view, url);
    }

    @Override
    public void onToolbarHide() {
        toolbar.animate().setInterpolator(mInterpolator)
                .setDuration(ANIM_DURATION)
                .translationY(-toolbar.getHeight());
    }

    @Override
    public void onToolbarShow() {
        toolbar.animate().setInterpolator(mInterpolator)
                .setDuration(ANIM_DURATION)
                .translationY(0);
    }
}
