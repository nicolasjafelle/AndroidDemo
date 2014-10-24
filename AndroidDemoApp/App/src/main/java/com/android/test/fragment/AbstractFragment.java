package com.android.test.fragment;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Surface;

import roboguice.fragment.RoboFragment;

public abstract class AbstractFragment<T> extends RoboFragment {

    protected T callbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
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

    /*
    If it can resolve the orientation returns
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        or
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    Else, returns "reportedOrientation"
     */
    public int resolveOrientation(int reportedOrientation){
        switch (reportedOrientation){
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            case ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
                return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            case ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR:
            case ActivityInfo.SCREEN_ORIENTATION_FULL_USER:
            case ActivityInfo.SCREEN_ORIENTATION_USER:
            case ActivityInfo.SCREEN_ORIENTATION_LOCKED:
            case ActivityInfo.SCREEN_ORIENTATION_NOSENSOR:
            case ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED:
            case ActivityInfo.SCREEN_ORIENTATION_SENSOR:
                switch (this.getActivity().getWindowManager().getDefaultDisplay().getRotation()){
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                }
        }

        return reportedOrientation;
    }

    public boolean isLandscape(int reportedOrientation){
        if (this.resolveOrientation(reportedOrientation)==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            return true;
        }else{
            return false;
        }
    }

    public boolean isPortrait(int reportedOrientation){
        return !this.isLandscape(reportedOrientation);
    }

}
