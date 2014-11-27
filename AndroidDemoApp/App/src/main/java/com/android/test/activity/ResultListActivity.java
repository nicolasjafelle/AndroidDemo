package com.android.test.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.android.test.R;
import com.android.test.domain.Venue;
import com.android.test.fragment.ResultListFragment;
import com.android.test.utils.DataHelper;


public class ResultListActivity extends AbstractActionBarActivity implements ResultListFragment.Callback {

    private final int ANIM_DURATION = 200;

    private Toolbar toolbar;

    private Interpolator mInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.material_toolbar);
        mInterpolator = new AccelerateDecelerateInterpolator();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setInitialFragment() {

        DataHelper dataHelper = null;
        String place = null;
        Location location = null;

        if(getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            dataHelper = (DataHelper) bundle.getSerializable(ResultListFragment.DATA_HELPER);
            place = bundle.getString(ResultListFragment.PLACE);
            location = bundle.getParcelable(ResultListFragment.LOCATION);
        }

        setInitialFragment(R.layout.activity_main_overlay, R.id.container, ResultListFragment.newInstance(dataHelper, place, location));
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
