package com.spendanalyzer.apppreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Sonia
 * This is singleton class for SharedPreference of application
 */
public class AppPreferences {

    public static AppPreferences mAppPreference;
    private SharedPreferences mPreferences;
    private Editor mEditor;

    /**
     * Enum tha wrap the various key that will be used in app shared preferences
     */
    enum SharedPrefrencesKey {
        userID,
        emailID,
    }

    public AppPreferences(Context context) {
        mPreferences = context.getSharedPreferences(PreferenceID.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (mAppPreference == null) {
            mAppPreference = new AppPreferences(context);
        }
        return mAppPreference;
    }

    public void clearAppPreference() {
        if (mPreferences != null) {
            mEditor.clear().commit();
        }
    }

    public void setUserId(int userId) {
        mEditor.putInt(SharedPrefrencesKey.userID.toString(), userId);
        mEditor.commit();
    }

    public int getUserId() {
        return mPreferences.getInt(SharedPrefrencesKey.userID.toString(), Integer.MIN_VALUE);
    }




    public void setEmailID(String emailID) {
        mEditor.putString(SharedPrefrencesKey.emailID.toString(), emailID);
        mEditor.commit();
    }

    public String getEmailID() {
        return mPreferences.getString(SharedPrefrencesKey.emailID.toString(), null);
    }


}

