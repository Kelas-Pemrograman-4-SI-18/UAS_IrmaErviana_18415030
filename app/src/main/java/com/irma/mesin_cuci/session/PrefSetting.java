package com.irma.mesin_cuci.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.irma.mesin_cuci.admin.HomeAdminActivity;
import com.irma.mesin_cuci.pembeli.HomePembeli;

public class PrefSetting {
    public static  String _id;
    public static String username;
    public static String namalengkap;
    public static String email;
    public static String notelp;
    public static String role;
    Activity activity;

    public PrefSetting(Activity activity) {
        this.activity = activity;
    }
    public SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = activity.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return  preferences;
    }

    public void isLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        if (session.isLoggedIn()) {
            pref = getSharedPreferences();
            _id = pref.getString("_id", "");
            username = pref.getString("username", "");
            namalengkap = pref.getString("namalengkap", "");
            email = pref.getString("email", "");
            notelp = pref.getString("notelp", "");
            role = pref.getString("role", "");
        }else {
            session.setLogin(false);
            session.setSessid(0);
            Intent i = new Intent(activity, activity.getClass());
            activity.startActivity(i);
            activity.finish();
        }
    }
    public void checkLogin(SessionManager session, SharedPreferences pref) {
        session = new SessionManager(activity);
        _id = pref.getString("_id", "");
        username = pref.getString("username", "");
        namalengkap = pref.getString("namalengkap", "");
        email = pref.getString("email", "");
        notelp = pref.getString("notelp", "");
        role = pref.getString("role", "");
        if (session.isLoggedIn()) {
            if (role.equals("1")) {
                Intent i = new Intent(activity, HomeAdminActivity.class);
                activity.startActivity(i);
                activity.finish();
            } else {
                Intent i = new Intent(activity, HomePembeli.class);
                activity.startActivity(i);
                activity.finish();
            }
        }
    }
    public void storeRegIdSharedPreferences(Context context, String _id,
                                            String username, String namalengkap, String email,
                                            String notelp, String role, SharedPreferences prefs){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("_id", _id);
        editor.putString("username", username);
        editor.putString("namalengkap", namalengkap);
        editor.putString("email", email);
        editor.putString("notelp", notelp);
        editor.putString("role", role);
        editor.commit();
    }

}
