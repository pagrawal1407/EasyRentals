package utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Parag on 9/7/2017.
 */

public class AppPreferences {
    public static final String KEY_PREFS_PHONE_NUMBER = "PhoneNumber";
    public static final String KEY_PREFS_DRIVING_LICENSE = "DrivingLicense";

    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName(); //  Name of the file -.xml
    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public AppPreferences(Context context) {
        this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getPhoneNumber() {
        return _sharedPrefs.getString(KEY_PREFS_PHONE_NUMBER, "");
    }

    public void savePhoneNumber(String text) {
        _prefsEditor.putString(KEY_PREFS_PHONE_NUMBER, text);
        _prefsEditor.commit();
    }

    public String getDrivingLicense() {
        return _sharedPrefs.getString(KEY_PREFS_PHONE_NUMBER, "");
    }

    public void saveDrivingLicense(String text) {
        _prefsEditor.putString(KEY_PREFS_PHONE_NUMBER, text);
        _prefsEditor.commit();
    }

}
