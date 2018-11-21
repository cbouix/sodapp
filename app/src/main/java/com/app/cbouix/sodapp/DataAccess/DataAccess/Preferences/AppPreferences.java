package com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by CBouix on 25/03/2017.
 */

public class AppPreferences {

    public static final String KEY_USUARIO = "KEY_USUARIO";
    public static final String KEY_NOMBRE_USUARIO = "KEY_NOMBRE_USUARIO";
    public static final String KEY_CONTRASENIA = "KEY_CONTRASENIA";
    public static final String KEY_REPARTIDOR = "KEY_REPARTIDOR";
    public static final String KEY_COD_REPARTIDOR = "KEY_COD_REPARTIDOR";
    public static final String KEY_IMPORTE_TOTAL = "KEY_IMPORTE_TOTAL";
    public static final String KEY_IMPORTE_PARCIAL = "KEY_IMPORTE_PARCIAL";
    public static final String KEY_IS_ADMIN = "KEY_IS_ADMIN";
    public static final String KEY_DIR_WS = "KEY_DIR_WS";
    public static final String KEY_SALDO_CLIENTE = "KEY_SALDO_CLIENTE";

    public static final String KEY_REMITO = "KEY_REMITO";
    public static final String KEY_COBRANZA = "KEY_COBRANZA";
    public static final String KEY_CLIENTE = "KEY_CLIENTE";

    public static final String KEY_REMITO_EDIT = "KEY_REMITO_EDIT";
    public static final String KEY_REMITO_EDIT_ID = "KEY_REMITO_EDIT_ID";
    public static final String KEY_COBRANZA_EDIT_ID = "KEY_COBRANZA_EDIT_ID";
    public static final String VERSION_REMITO = "VERSION_REMITO";
    public static final String VERSION_COBRANZA = "VERSION_COBRANZA";

    public static final String KEY_POSITION = "KEY_POSITION";

    private static String KEY_SHARED_PREFERENCE = "KEY_SHARED_PREFERENCE";

    //region STRING
    public static void setString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        // Commit the edits!
        editor.commit();
    }

    public static String getString(Context context, String key,
                                   String defaultValue) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        return settings.getString(key, defaultValue);
    }
    //endregion

    //region INTEGER
    public static void setInt(Context context, String key, int value){
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key,
                                   int defaultValue) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        return settings.getInt(key, defaultValue);
    }
    //endregion

    //region LONG
    public static void setLong(Context context, String key, long value){
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key,
                               long defaultValue) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        return settings.getLong(key, defaultValue);
    }
    //endregion

    //region BOOLEAN
    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key,
                               boolean defaultValue) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(
                KEY_SHARED_PREFERENCE, 0);
        return settings.getBoolean(key, defaultValue);
    }
    //endregion
}
