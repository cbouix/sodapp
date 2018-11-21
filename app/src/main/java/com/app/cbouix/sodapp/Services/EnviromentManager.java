package com.app.cbouix.sodapp.Services;

import android.content.Context;
import android.text.TextUtils;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;

/**
 * Created by CBouix on 17/04/2017.
 */

public class EnviromentManager {

    private static String URL_v1 = "";
    private final static String USER_ADMIN = "administrador";
    private final static String PASSWORD_ADMIN = "E5510";

    public static String getUrl(Context context){
        return AppPreferences.getString(context, AppPreferences.KEY_DIR_WS, "");
    }

    public static boolean isAdmin(String user, String password){
        return USER_ADMIN.equalsIgnoreCase(user) && PASSWORD_ADMIN.equalsIgnoreCase(password);
    }

    public static void changeDireccionWS(Context context, String urlWS){
        if(!urlWS.endsWith("/")){
            urlWS = urlWS + ("/");
        }
        AppPreferences.setString(context, AppPreferences.KEY_DIR_WS, urlWS);
        URL_v1 = urlWS;
    }

    public static boolean isEmpty(Context context){
        return TextUtils.isEmpty(AppPreferences.getString(context, AppPreferences.KEY_DIR_WS, ""));
    }

}
