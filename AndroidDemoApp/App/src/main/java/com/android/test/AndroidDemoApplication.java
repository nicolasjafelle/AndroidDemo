package com.android.test;

import android.app.Application;
import android.content.Context;

import com.android.test.module.AndroidDemoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.qachee.ExpirationTime;
import com.qachee.QacheeManager;

/**
 * Created by nicolas on 11/22/14.
 */
public class AndroidDemoApplication extends Application {

    private static AndroidDemoApplication instance;
    private static final Injector INJECTOR = Guice.createInjector(new AndroidDemoModule());

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        QacheeManager.getInstance().setExpirationTime(ExpirationTime.THIRTY_SECONDS);
    }

    public static Context getAppContext() {
        return instance;
    }

    public static void injectMembers(final Object object) {
        INJECTOR.injectMembers(object);
    }

}
