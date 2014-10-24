package com.android.test.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.test.R;
import com.android.test.fragment.MainFragment;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

public class MainActivity extends RoboActionBarActivity {

    @InjectView(R.id.material_toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoboGuice.getInjector(this).injectViewMembers(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {
	        getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, MainFragment.newInstance())
                    .commit();
        }
    }

}
