package com.android.test.task.event;

import com.android.test.dto.FoursquareApiErrorDto;

/**
 * Created by Nicolas Jafelle on 11/28/14.
 */
public class OnApiErrorEvent {

    public FoursquareApiErrorDto errorDto;

    public OnApiErrorEvent(FoursquareApiErrorDto errorDto) {
        this.errorDto = errorDto;

    }
}
