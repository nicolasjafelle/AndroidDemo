package com.android.test.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.android.test.R;
import com.android.test.domain.Venue;
import com.android.test.fragment.MainFragment;
import com.android.test.utils.DataHelper;

import java.util.List;

import roboguice.inject.InjectView;

public class MainActivity extends AbstractActionBarActivity implements MainFragment.Callback {

    @InjectView(R.id.material_toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu);
    }

    @Override
    protected void setInitialFragment() {
        setInitialFragment(R.layout.activity_main, R.id.container, MainFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public void onResult(List<Venue> venues, Location currentLocation) {
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitionView, EXTRA_IMAGE);
//        Intent intent = new Intent(this, ResultListActivity.class);
//        intent.putExtra(EXTRA_IMAGE, url);
//        ActivityCompat.startActivity(activity, intent, options.toBundle());

        Intent intent = new Intent(this, ResultListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LISTA", new DataHelper(venues));
        if(currentLocation != null) {
            bundle.putParcelable("LOCATION", currentLocation);
        }

        intent.putExtras(bundle);
        startActivity(intent);
//        ActivityCompat.startActivity(this, intent, bundle);
    }
}
