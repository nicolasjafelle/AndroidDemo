package com.android.test.session;

import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class SessionManager {

    @Inject
    private SharedPreferences sharedPref;

    private static final String SAVED_PLACES = "saved_places";

    @Inject
    public SessionManager() {}


    public void savePlace(String place) {
        Set<String> locations = new HashSet<String>(getSavedPlaces());
        locations.add(place);
        saveValues(SAVED_PLACES, locations);
    }

    public Set<String> getSavedPlaces() {
        return this.sharedPref.getStringSet(SAVED_PLACES, new HashSet<String>());
    }

    public void clear() {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.clear();
        editor.commit();
    }


    // Helpers
    protected void saveValue(String key, String value){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected void saveValues(String key, Set<String> values){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }



    protected void saveValue(String key, long value){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putLong(key,value);
        editor.commit();
    }

    protected void saveValue(String key, int value){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    protected void saveValue(String key, boolean value){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
