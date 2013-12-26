package com.android.test.domain;

import java.io.Serializable;

/**
 * Created by nicolas on 12/22/13.
 */
public class Venue implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Location location;
	private Contact contact;
	// for this test we don't need additional attributes like categories and stats.

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
}
