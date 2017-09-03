package com.app.easyretrofit.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.Set;

public class Preferences {

    private Context context;
    Set<String> strings;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Preferences(Context context) {
        this.context = context;
    }

    protected SharedPreferences getSharedPreferences(String key) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getString(String key, String def) {
        SharedPreferences prefs = getSharedPreferences(key);
        String s = prefs.getString(key, def);
        return s;
    }

    public void setString(String key, String val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putString(key, val);
        e.commit();
    }

    public boolean getBoolean(String key, boolean def) {
        SharedPreferences prefs = getSharedPreferences(key);
        boolean b = prefs.getBoolean(key, def);
        return b;
    }

    public void setBoolean(String key, boolean val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putBoolean(key, val);
        e.commit();
    }

    private Set<String> getStringSet(String key, Set<String> def) {
        SharedPreferences prefs = getSharedPreferences(key);
        Set<String> s = prefs.getStringSet(key, def);
        return s;
    }

    public void setStringSet(String key, Set<String> val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putStringSet(key, val);
        e.commit();
    }


}
