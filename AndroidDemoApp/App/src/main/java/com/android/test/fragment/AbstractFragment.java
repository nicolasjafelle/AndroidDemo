package com.android.test.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import roboguice.fragment.RoboFragment;

public abstract class AbstractFragment<T> extends RoboFragment {

    protected T callbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacks = (T) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Callback interface");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.callbacks = null;
    }


    protected ActionBarActivity getActionBarActivity() {
        return (ActionBarActivity) getActivity();
    }

}
