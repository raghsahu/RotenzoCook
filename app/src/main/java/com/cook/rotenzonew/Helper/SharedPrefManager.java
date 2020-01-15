package com.cook.rotenzonew.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cook.rotenzonew.Activity.LoginActivity;
import com.cook.rotenzonew.Model.User;

public class SharedPrefManager {


    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_ID = "keyid";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_LAT = "keylat";
    private static final String KEY_LNG = "keYlng";
    private static final String KEY_STATUS = "keystatus";
    private static final String KEY_IMAGE = "keyimage";
    private static final String KEY_EMAILSTATUS = "keystatus";
    private static final String KEY_MOBILESTATUS = "keymobilestatus";
    private static final String KEY_CREATESAT = "keycreatedat";
    private static final String KEY_TYPE = "keytype";
    private static final String KEY_FACEBOOKID = "keyfacebookid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_LAT, user.getLat());
        editor.putString(KEY_LNG, user.getLng());
        editor.putString(KEY_STATUS, user.getStatus());
        editor.putString(KEY_IMAGE, user.getImage());
        editor.putString(KEY_EMAILSTATUS, user.getEmail_status());
        editor.putString(KEY_MOBILESTATUS, user.getMobile_status());
        editor.putString(KEY_TYPE, user.getType());
        editor.putString(KEY_FACEBOOKID, user.getFacebook_id());


        editor.apply();
    }


    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PHONE, null),

                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_LAT, null),
                sharedPreferences.getString(KEY_LNG, null),

                sharedPreferences.getString(KEY_STATUS, null),
                sharedPreferences.getString(KEY_IMAGE, null),
                sharedPreferences.getString(KEY_EMAILSTATUS, null),

                sharedPreferences.getString(KEY_MOBILESTATUS, null),
                sharedPreferences.getString(KEY_TYPE, null),
                sharedPreferences.getString(KEY_FACEBOOKID, null)


        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}