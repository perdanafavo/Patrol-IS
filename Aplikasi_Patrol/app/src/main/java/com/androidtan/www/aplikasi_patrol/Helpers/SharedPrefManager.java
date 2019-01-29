package com.androidtan.www.aplikasi_patrol.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_PATROLIS_APP = "patrolis";

    public static final String SP_IDUSER= "spIdUser";
    public static final String SP_FULLNAME = "spFullname";
    public static final String SP_ALREADY_LOGIN = "spAlreadyLogin";
    public static final String SP_ALREADY_ABSENSI = "spAlreadyAbsensi";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_PATROLIS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public void deleteSP(String keySP) {
        spEditor.remove(keySP).commit();
    }

    public String getSpPatrolisApp() {
        return SP_PATROLIS_APP;
    }

    public int getSpIduser() {
        return sp.getInt(SP_IDUSER, 0);
    }

    public String getSpFullname() {
        return sp.getString(SP_FULLNAME, "");
    }

    public String getSpAbsensi() {
        return sp.getString(SP_ALREADY_ABSENSI, "");
    }

    public Boolean getSpAlreadyLogin() {
        return sp.getBoolean(SP_ALREADY_LOGIN, false);
    }
}
