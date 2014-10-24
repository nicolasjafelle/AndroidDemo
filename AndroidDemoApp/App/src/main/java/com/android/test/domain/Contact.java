package com.android.test.domain;

import java.io.Serializable;

/**
 * Created by nicolas on 12/22/13.
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

	private String formattedPhone;

	public String getFormattedPhone() {
		return formattedPhone;
	}

	public void setFormattedPhone(String formattedPhone) {
		this.formattedPhone = formattedPhone;
	}
}
