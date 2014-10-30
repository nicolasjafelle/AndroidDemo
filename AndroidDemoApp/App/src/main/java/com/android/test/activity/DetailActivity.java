package com.android.test.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.test.R;
import com.android.test.fragment.DetailFragment;
import com.android.test.view.VenueItemView;

import roboguice.inject.InjectView;

public class DetailActivity extends AbstractActionBarActivity {

    @InjectView(R.id.material_toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent();
    }

    @Override
    protected void setInitialFragment() {
        setInitialFragment(R.layout.activity_main, R.id.container, DetailFragment.newInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, ResultListActivity.class));
                animate();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animate();
    }

    public static void startActivity(AbstractActionBarActivity activity, View transitionView, String url) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                Pair.create(transitionView, DetailFragment.ANIMATED_IMAGE),
                Pair.create(transitionView, DetailFragment.ANIMATED_NAME),
                Pair.create(transitionView, DetailFragment.ANIMATED_DISTANCE));

        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailFragment.ANIMATED_IMAGE, url);
        intent.putExtra(DetailFragment.ANIMATED_NAME, ((VenueItemView)transitionView).getNameText() );
        intent.putExtra(DetailFragment.ANIMATED_DISTANCE, ((VenueItemView)transitionView).getDistanceText() );

        ActivityCompat.startActivity(activity, intent, options.toBundle());
        animate(activity);
    }

    private static void animate(AbstractActionBarActivity activity) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        }

    }

    private void animate() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
        }

    }


}
