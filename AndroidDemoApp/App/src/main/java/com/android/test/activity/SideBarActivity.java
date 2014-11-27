package com.android.test.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.test.R;
import com.android.test.domain.Venue;
import com.android.test.fragment.MainFragment;
import com.android.test.fragment.ResultListFragment;
import com.android.test.utils.DataHelper;
import com.android.test.view.SideBarCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SideBarActivity extends AbstractActionBarActivity implements MainFragment.Callback, AdapterView.OnItemClickListener {

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    private ListView listView;

    private ActionBarDrawerToggle drawerToggle;
    private SideBarCallback sideBarCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) findViewById(R.id.material_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        listView = (ListView) findViewById(R.id.activity_side_bar_list_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_menu);
        setupSideBar();
    }

    @Override
    protected void setInitialFragment() {
        setInitialFragment(R.layout.activity_side_bar, R.id.container, MainFragment.newInstance());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_menu, menu);
//        return true;
//    }

    private void setupSideBar() {
        if (toolbar != null) {
            drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawerLayout.isDrawerOpen(Gravity.START)) {
                        drawerLayout.closeDrawer(Gravity.START);
                    } else {
                        drawerLayout.openDrawer(Gravity.START);
                    }
                }
            });
        }


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public void onResult(List<Venue> venues, Location currentLocation, String place) {
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitionView, EXTRA_IMAGE);
//        Intent intent = new Intent(this, ResultListActivity.class);
//        intent.putExtra(EXTRA_IMAGE, url);
//        ActivityCompat.startActivity(activity, intent, options.toBundle());

        Intent intent = new Intent(this, ResultListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ResultListFragment.DATA_HELPER, new DataHelper(venues));
        bundle.putString(ResultListFragment.PLACE, place);

        if(currentLocation != null) {
            bundle.putParcelable(ResultListFragment.LOCATION, currentLocation);
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void loadSavedPlaces(Set<String> savedPlaces, SideBarCallback sideBarCallback) {
        this.sideBarCallback = sideBarCallback;
        listView.setOnItemClickListener(this);
        List<String> list = new ArrayList<String>(savedPlaces);
        ArrayAdapter<String> savedPlacesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(savedPlacesAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String place = (String) parent.getItemAtPosition(position);
        this.sideBarCallback.onSideBarItemClick(place);
        drawerLayout.closeDrawer(Gravity.START);
    }
}
