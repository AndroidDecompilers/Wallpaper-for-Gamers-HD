package com.kkinfosis.gamingwallpaper4k.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceHelper {
    private static final String PREF_FILE = "fucker";
    private Editor _editorPref;
    private SharedPreferences _pref;

    public SharedPrefrenceHelper(Context context) {
        this._pref = context.getSharedPreferences(PREF_FILE, 0);
        _editorPref = this._pref.edit();
    }

    public void saveString(String str, String str2) {
        this._editorPref.putString(str, str2);
        this._editorPref.commit();
    }

    public String getString(String str) {
        return this._pref.getString(str, "");
    }

    public void saveInt(String str, int i) {
        this._editorPref.putInt(str, i);
        this._editorPref.commit();
    }

    public int getInt(String str) {
        return this._pref.getInt(str, 0);
    }

    public void clearSharedRefrence() {
        this._pref.edit().clear().commit();
    }

    public void saveBoolean(String str, boolean z) {
        this._editorPref.putBoolean(str, z);
        this._editorPref.commit();
    }

    public boolean getBoolen(String str) {
        return this._pref.getBoolean(str, false);
    }
}
