package com.android.test.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.android.test.R;
import com.android.test.domain.Venue;


/**
 * This Dialog is just an indeterminet progress, typically used for Background Tasks.
 */
public class VenueDialogFragment extends DialogFragment {

	public static final String SELECTED_VENUE = "selected_venue";

	/**
	 * Callback used when the user cancel or press the back button to cancel the task.
	 */
	public interface ProgressDialogFragmentListener {
		void onCancel();
	}

	public static VenueDialogFragment newInstance() {
		return new VenueDialogFragment();
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
		Venue venue = null;
		if(getArguments() != null) {
			venue = (Venue) getArguments().get(SELECTED_VENUE);
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setMessage(venue.getContact().getFormattedPhone() + "\n" +
						   venue.getLocation().getAddress() + "\n" +
						   venue.getLocation().getCity() + "\n" +
						   venue.getLocation().getState())
			.setTitle(venue.getName())
			.setNeutralButton(R.string.cancel, listener);

		return builder.create();
	}


	private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dismiss();
		}
	};

}
