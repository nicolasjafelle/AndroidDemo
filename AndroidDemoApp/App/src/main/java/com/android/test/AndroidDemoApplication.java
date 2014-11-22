package com.android.test;

import android.app.Application;

import com.qachee.ExpirationTime;
import com.qachee.QacheeManager;

/**
 * Created by nicolas on 11/22/14.
 */
public class AndroidDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        QacheeManager.getInstance().setExpirationTime(ExpirationTime.THIRTY_SECONDS);
    }
}
