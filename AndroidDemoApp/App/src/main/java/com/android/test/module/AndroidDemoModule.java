package com.android.test.module;

import android.content.Context;

import com.android.test.AndroidDemoApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;

/**
 * Created by Nicolas Jafelle on 11/26/14.
 */
public class AndroidDemoModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Context.class).toProvider(new Provider<Context>() {
            @Override
            public Context get() {
                return AndroidDemoApplication.getAppContext();
            }
        });
//        .in(Singleton.class);
    }
}
