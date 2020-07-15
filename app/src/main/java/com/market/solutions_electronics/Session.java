package com.market.solutions_electronics;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setemail(String email) {
        prefs.edit().putString("email", email).commit();
    }

    public String getemail() {
        String email = prefs.getString("email","");
        return email;
    }
}
