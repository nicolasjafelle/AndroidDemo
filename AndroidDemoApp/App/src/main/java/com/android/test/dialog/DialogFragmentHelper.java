package com.android.test.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Dialog fragment helper
 */
public abstract class DialogFragmentHelper extends DialogFragment {

	private static final String TAG = "dialog_fragment_helper";

	/**
	 * Show dialog
	 */
	public static void show(FragmentActivity activity, DialogFragment fragment, Bundle arguments) {
		FragmentManager manager = activity.getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		Fragment current = manager.findFragmentByTag(TAG);
		if (current != null) {
			transaction.remove(current);
			//transaction.addToBackStack(null);
			transaction.commit();
		}
		if(fragment.getArguments() == null) {
			fragment.setArguments(arguments);
		}else {
			fragment.getArguments().putAll(arguments);
		}

		fragment.show(manager, TAG);
	}

    /**
     * Dismiss current DialogFragment
     * @param activity
     */
    public static void dismissDialog(FragmentActivity activity){
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismissAllowingStateLoss();
        }
    }
}
