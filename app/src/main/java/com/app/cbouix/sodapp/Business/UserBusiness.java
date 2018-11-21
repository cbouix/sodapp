package com.app.cbouix.sodapp.Business;

import android.content.Context;
import android.provider.Settings;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Repartidor;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by CBouix on 21/04/2017.
 */

public class UserBusiness {

    private static final String LOGIN = "Login/%s,%s";
    private static final String UPDATE_GCM = "updateGCM/%s";

    public static Repartidor login(Context context, String userName, String password) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + LOGIN, userName, password);

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String repartidorStr= gson.fromJson(reader, String.class);
        Repartidor repartidor = gson.fromJson(repartidorStr, Repartidor.class);
        return repartidor;
    }

    public static void updateGcm(Context context, String token) throws IOException, JSONException {

        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        JSONObject payload = new JSONObject();
        payload.put("ID", AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));
        payload.put("Codigo", AppPreferences.getString(context, AppPreferences.KEY_COD_REPARTIDOR, ""));
        payload.put("Nombre", AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        payload.put("AndroidId", android_id);
        payload.put("GCMRegId", token);
        payload.put("UsuarioId", AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));
        payload.put("Login", AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        payload.put("EsAdministrador", "No");

        String url = String.format(EnviromentManager.getUrl(context) + UPDATE_GCM,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));
        UtilsServices.getStreamPut(url, payload.toString());
    }
}
