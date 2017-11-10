package utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Parag on 10/5/2017.
 */

public class UserPreferences {

    public static final String KEY_PREFS_FIRST_NAME = "FirstName";
    public static final String KEY_PREFS_LAST_NAME = "LastName";
    public static final String KEY_PREFS_EMAIL = "Email";


    private static final String USER_SHARED_PREFS = UserPreferences.class.getSimpleName();
    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public UserPreferences(Context context) {
        this._sharedPrefs = context.getSharedPreferences(USER_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }

    public String getFirstName() {
        return _sharedPrefs.getString(KEY_PREFS_FIRST_NAME , "");
    }

    public String getLastName() {
        return _sharedPrefs.getString(KEY_PREFS_LAST_NAME, "");
    }

    public String getEmail() {
        return _sharedPrefs.getString(KEY_PREFS_EMAIL , "");
    }

    public void saveFirstName(String text) {
        _prefsEditor.putString(KEY_PREFS_FIRST_NAME, text);
        _prefsEditor.commit();
    }

    public void saveLastName(String text) {
        _prefsEditor.putString(KEY_PREFS_LAST_NAME, text);
        _prefsEditor.commit();
    }

    public void saveEmail(String text) {
        _prefsEditor.putString(KEY_PREFS_EMAIL, text);
        _prefsEditor.commit();
    }

    public Boolean contains(String text){
        return _sharedPrefs.contains(text);
    }
}
