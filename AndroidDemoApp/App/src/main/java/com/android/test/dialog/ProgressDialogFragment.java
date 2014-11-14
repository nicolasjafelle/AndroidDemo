package com.android.test.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import roboguice.fragment.RoboDialogFragment;


/**
 * This Dialog is just an indeterminet progress, typically used for Background Tasks.
 */
public class ProgressDialogFragment extends RoboDialogFragment {

	/**
	 * Callback used when the user cancel or press the back button to cancel the task.
	 */
	public interface ProgressDialogFragmentListener {
		void onCancel();
	}

	public static ProgressDialogFragment newInstance() {
		return new ProgressDialogFragment();
	}

	public static final String MESSAGE = "message";
	private ProgressDialogFragmentListener listener;

	public void setProgressDialogFragmentListener(ProgressDialogFragmentListener listener) {
		this.listener = listener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRetainInstance(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}


    @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final ProgressDialog dialog = new ProgressDialog(getActivity());

		dialog.setMessage(getArguments().getString(MESSAGE));
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

    public boolean isShowing() {
        boolean isShowing = false;
        if(getDialog() != null) {
            isShowing = getDialog().isShowing();
        }
        return isShowing;
    }

    /**
     * Workaround for an open bug in DialogFragment.
     * https://code.google.com/p/android/issues/detail?id=17423
     */
    @Override
    public void onDestroyView() {
        if(getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    public void setMessage(int resId) {
		((ProgressDialog)getDialog()).setMessage(getString(resId));
	}

    @Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		if (listener != null) {
			listener.onCancel();
		}
	}

}
