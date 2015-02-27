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

import com.android.test.AndroidDemoApplication;
import com.android.test.R;
import com.android.test.client.FoursquareClient;
import com.android.test.dialog.DialogFragmentHelper;
import com.android.test.dialog.ProgressDialogFragment;
import com.android.test.domain.Venue;
import com.android.test.dto.ErrorType;
import com.android.test.dto.FoursquareApiErrorDto;
import com.android.test.location.GPSTracker;
import com.android.test.otto.OttoBus;
import com.android.test.otto.VenueResultEvent;
import com.android.test.otto.VenueSearchEvent;
import com.android.test.qachee.QacheeData;
import com.android.test.session.SessionManager;
import com.android.test.task.FoursquareAsyncTask;
import com.android.test.task.VenueBackgroundTask;
import com.android.test.task.event.OnApiErrorEvent;
import com.android.test.task.event.OnFinallyEvent;
import com.android.test.task.event.OnPreExecuteEvent;
import com.android.test.view.SideBarCallback;
import com.qachee.QacheeManager;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * MainFragment
 * Created by nicolas on 12/22/13.
 */
public class MainFragment extends AbstractFragment<MainFragment.Callback>
        implements SideBarCallback, ProgressDialogFragment.ProgressDialogFragmentListener {




    public interface Callback {
        void onResult(List<Venue> venues, Location currentLocation, String place);
        void loadSavedPlaces(Set<String> savedPlaces, SideBarCallback sideBarCallback);
    }

	private EditText editText;

	private Button searchButton;

    private FoursquareAsyncTask asyncTask;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private FoursquareClient foursquareClient;

    @Inject
    private OttoBus ottoBus;

	private ProgressDialogFragment progressDialog;

    @Inject
	private GPSTracker gpsTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment newInstance() {
		return new MainFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        editText = (EditText) view.findViewById(R.id.fragment_main_edittext);
        searchButton = (Button) view.findViewById(R.id.fragment_main_button);

        return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

        progressDialog = ProgressDialogFragment.newInstance();

		searchButton.setOnClickListener(onClickListener);
	}

    @Override
    public void onResume() {
        super.onResume();
        loadSavedPlaces();
    }

    private void createProgressDialog(int resId) {
		Bundle arguments = new Bundle();
        progressDialog = ProgressDialogFragment.newInstance();
        progressDialog.setProgressDialogFragmentListener(this);
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
                    postVenueSearchEvent(place);
				}else{
					gpsTracker.showSettingsAlert();
				}
			}
		}
	};


    @Override
    public void onSideBarItemClick(String text) {
        if(gpsTracker.canGetLocation()){
            postVenueSearchEvent(text);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void postVenueSearchEvent(String text) {
        ottoBus.post(new VenueSearchEvent(text));
    }

    private void loadSavedPlaces() {
        Set<String> savedPlaces = sessionManager.getSavedPlaces();

        if(!savedPlaces.isEmpty()) {
            callbacks.loadSavedPlaces(savedPlaces, this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ottoBus.register(this);
    }

    @Override
    public void onStop() {
        super.onPause();
        ottoBus.unregister(this);
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
		gpsTracker.stopUsingGPS();
	}


    @Override
    public void onCancel() {
        cancelTask();
    }

    private void cancelTask() {
        if(asyncTask != null && !asyncTask.isCancelled()) {
            asyncTask.cancel(true);
        }
    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Inject
    AndroidDemoApplication application;

    /* ********************************************************* */
    /* **************** Otto Subscribers *********************** */
    /* ********************************************************* */

    @Subscribe
    public void searchForVenues(VenueSearchEvent event) {

        QacheeData data = (QacheeData) QacheeManager.getInstance().get(event.place, true);

        if(data == null) {
            asyncTask = new VenueBackgroundTask(event.place, gpsTracker.getLocation());
            asyncTask.execute();
        }else {
            ottoBus.post(new VenueResultEvent(data.venues, event.place, gpsTracker.getLocation()));
        }
    }

    @Subscribe
    public void resultVenues(VenueResultEvent event) {
        if(event.venues == null || event.venues.size() == 0) {
            Toast.makeText(getActivity(), R.string.no_results_found, Toast.LENGTH_SHORT).show();
        }else {
            QacheeManager.getInstance().add(new QacheeData(event.place, event.venues));
            callbacks.onResult(event.venues, event.location, event.place);
        }
    }

    @Subscribe
    public void onPreExecute(OnPreExecuteEvent event) {
        createProgressDialog(R.string.connecting_to_foursquare);
    }

    @Subscribe
    public void onApiError(OnApiErrorEvent event) {
        FoursquareApiErrorDto errorDto = event.errorDto;

        if(errorDto.getMeta().getErrorType() == ErrorType.failed_geocode) {
            Toast.makeText(getActivity(), R.string.no_results_found, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), R.string.unknown_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onFinally(OnFinallyEvent event) {
        closeKeyboard();
        DialogFragmentHelper.dismissDialog(getActivity());
    }


}
