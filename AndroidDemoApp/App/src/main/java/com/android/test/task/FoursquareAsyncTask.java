package com.android.test.task;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.test.R;
import com.android.test.dto.FoursquareApiErrorDto;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit.RetrofitError;
import roboguice.util.RoboAsyncTask;

/**
 * Created by nicolas on 12/22/13.
 */
public abstract class FoursquareAsyncTask<T> extends RoboAsyncTask<T> {

    protected FoursquareAsyncTask(Context context) {
        super(context);
    }

    @Override
	protected void onException(Exception e) throws RuntimeException {
		try {
			if(e instanceof RetrofitError) {
				RetrofitError retrofitError = (RetrofitError) e;

				if(retrofitError.getResponse() != null) {
					if (retrofitError.getResponse().getStatus() > 500 ){
						String msg = "Network error HTTP ("+retrofitError.getResponse().getStatus()+")";
						if (retrofitError.getMessage()!=null && !retrofitError.getMessage().isEmpty()){
							msg += ": "+retrofitError.getMessage();
						}
						super.onException(e);
					}else if (retrofitError.getBody()==null){
						Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					}else if (retrofitError.getCause() instanceof ConnectException){
						Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
					}else if (retrofitError.getCause() instanceof SocketTimeoutException){
						Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
					}else{
						BufferedReader reader = new BufferedReader(new InputStreamReader(((RetrofitError) e).getResponse().getBody().in()));
						FoursquareApiErrorDto errorDto = new Gson().fromJson(reader, FoursquareApiErrorDto.class);
						onApiError(errorDto);
					}
				}else if(retrofitError.isNetworkError()){
					Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
				}
			}else {
				super.onException(e);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	protected void onInterrupted(Exception e) {
		Log.d("BACKGROUND_TASK", "Interrupting background task " + this);
	}

	protected abstract void onApiError(FoursquareApiErrorDto errorDto);

	public final Context getContext() {
		return this.context;
	}

}
