package com.android.test.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import roboguice.RoboGuice;
import roboguice.activity.RoboActionBarActivity;

public abstract class AbstractActionBarActivity extends RoboActionBarActivity {

	private FrameLayout mainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (savedInstanceState == null) {
			setInitialFragment();
//		}

//        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	/**
	 * This method defines which is the initial Fragment. Classes that extends
	 * this Class should override it and tells the Parent Class which is the
	 * initial fragment to load.
	 */
	protected abstract void setInitialFragment();


	/**
	 * This method loads the initial fragment, it should be called inside
	 * setInitialFragment().
	 *
	 * @param layoutResId - the activity layout
	 * @param viewId      - the Main view id.
	 * @param fragment    - the initial Fragment
	 */
	protected void setInitialFragment(int layoutResId, int viewId, Fragment fragment) {
		setContentView(layoutResId);
        RoboGuice.getInjector(this).injectViewMembers(this);
		mainLayout = (FrameLayout) findViewById(viewId);
		setInitialFragment(mainLayout, fragment);
	}

	/**
	 * This method loads the initial fragment, it should be called inside
	 * setInitialFragment().
	 *
	 * @param viewId      - the Main view id.
	 * @param fragment    - the initial Fragment
	 */
	protected void setInitialFragment(int viewId, Fragment fragment) {
		mainLayout = (FrameLayout) findViewById(viewId);
		setInitialFragment(mainLayout, fragment);
	}

	/**
	 * This method loads the initial fragment
	 *
	 * @param view     - the Main View, it is recommended to use a FrameLayout
	 * @param fragment - the initial fragment
	 */
	private void setInitialFragment(View view, Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(view.getId(), fragment).commit();
	}

	/**
	 * This method replace the existing fragment with a current one. This new
	 * fragment is added to the back stack.
	 *
	 * @param newFragment - The new Fragment that will replace the current visible fragment.
	 */
    protected void replaceFragment(Fragment newFragment) {
		this.replaceFragment(newFragment, true);
	}

	/**
	 * This method replace the existing fragment with a current one. This new
	 * fragment could be added or not to the back stack.
	 *
	 * @param newFragment    The new Fragment that will replace the current visible fragment.
	 * @param addToBackStack True if the new Fragment should be added to the back stack, false otherwise.
	 */
    protected void replaceFragment(Fragment newFragment, boolean addToBackStack) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		if (addToBackStack) {
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
	    fragmentTransaction.replace(mainLayout.getId(), newFragment).commit();
	}

}
