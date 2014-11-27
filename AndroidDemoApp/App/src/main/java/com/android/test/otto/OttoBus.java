package com.android.test.otto;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

/**
 * Injected Class to use Otto with a Singleton instance and to post always on the main thread.
 * <br>
 * https://github.com/square/otto/issues/38
 */
@Singleton
public class OttoBus extends Bus {



    private final Handler mainThread = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    OttoBus.super.post(event);
                }
            });
        }
    }

}