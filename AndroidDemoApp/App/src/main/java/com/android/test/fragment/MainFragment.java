package com.android.test.fragment;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.client.FoursquareClient;
import com.android.test.dialog.DialogFragmentHelper;
import com.android.test.dialog.ProgressDialogFragment;
import com.android.test.domain.Venue;
import com.android.test.dto.ErrorType;
import com.android.test.dto.FoursquareApiErrorDto;
import com.android.test.dto.VenueDto;
import com.android.test.location.GPSTracker;
import com.android.test.session.SessionManager;
import com.android.test.task.FoursquareAsyncTask;
import com.android.test.view.SideBarCallback;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import roboguice.inject.InjectView;

/**
 * MainFragment
 * Created by nicolas on 12/22/13.
 */
public class MainFragment extends AbstractFragment<MainFragment.Callback> implements SideBarCallback {

    public interface Callback {
        void onResult(List<Venue> venues, Location currentLocation, String place);
        void loadSavedPlaces(Set<String> savedPlaces, SideBarCallback sideBarCallback);
    }

    @InjectView(R.id.fragment_main_edittext)
	private EditText editText;

    @InjectView(R.id.fragment_main_button)
	private Button searchButton;

    @Inject
    private SessionManager sessionManager;

	private ProgressDialogFragment progressDialog;


	private GPSTracker gpsTracker;

	public static Fragment newInstance() {
		return new MainFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        progressDialog = ProgressDialogFragment.newInstance();
        gpsTracker = new GPSTracker(getActivity());

		searchButton.setOnClickListener(onClickListener);
	}

    @Override
    public void onResume() {
        super.onResume();
        loadSavedPlaces();
    }

    private void createProgressDialog(int resId) {
		Bundle arguments = new Bundle();
		arguments.putString(ProgressDialogFragment.MESSAGE, getString(resId));
		DialogFragmentHelper.show(getActivity(), progressDialog, arguments);
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String place = editText.getText().toString().trim();

			if(place == null || place.length() == 0) {
				Toast.makeText(getActivity(), R.string.edit_text_empty, Toast.LENGTH_SHORT).show();
			}else {
				// check if GPS enabled
				if(gpsTracker.canGetLocation()){
					new VenueTask(getActivity(), place, gpsTracker.getLocation()).execute();
				}else{
					gpsTracker.showSettingsAlert();
				}
			}
		}
	};

    @Override
    public void onSideBarItemClick(String text) {
        if(gpsTracker.canGetLocation()){
            new VenueTask(getActivity(), text, gpsTracker.getLocation()).execute();
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void loadSavedPlaces() {
        Set<String> savedPlaces = sessionManager.getSavedPlaces();

        if(!savedPlaces.isEmpty()) {
            callbacks.loadSavedPlaces(savedPlaces, this);
        }
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		gpsTracker.stopUsingGPS();
	}




	/**
	 * VenueTask
	 */
	public class VenueTask extends FoursquareAsyncTask<VenueDto> {

		private String criteria;
		private Location currentLocation;

		public VenueTask(Context context, String criteria, Location currentLocation) {
			super(context);
			this.criteria = criteria;
			this.currentLocation = currentLocation;
		}

		@Override
		protected void onPreExecute() throws Exception {
			super.onPreExecute();
			createProgressDialog(R.string.connecting_to_foursquare);
		}

		@Override
		public VenueDto call() throws Exception {
			return FoursquareClient.getInstance().searchForVenues(this.criteria);
		}

		@Override
		protected void onSuccess(VenueDto venueDto) throws Exception {
			super.onSuccess(venueDto);
			List<Venue> venues = venueDto.getResponse().getVenues();

			if(venues == null && venues.size() > 0) {
				Toast.makeText(getContext(), R.string.no_results_found, Toast.LENGTH_SHORT).show();
			}else {
                callbacks.onResult(venues, currentLocation, criteria);
			}
		}

		@Override
		protected void onApiError(FoursquareApiErrorDto errorDto) {
			if(errorDto.getMeta().getErrorType() == ErrorType.failed_geocode) {
				Toast.makeText(getContext(), R.string.no_results_found, Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getContext(), R.string.unknown_error, Toast.LENGTH_SHORT).show();
			}
		}

		private void closeKeyboard() {
			InputMethodManager imm = (InputMethodManager)getContext().getSystemService(
				Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}

		@Override
		protected void onFinally() throws RuntimeException {
			super.onFinally();
			closeKeyboard();
			progressDialog.dismiss();
		}
	}

}
