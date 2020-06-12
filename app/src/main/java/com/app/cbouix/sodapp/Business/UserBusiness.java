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
    private static final String LOGIN2 = "Login2";
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

    public static Repartidor login2(Context context, String userName, String password) throws IOException, JSONException {

        String url = EnviromentManager.getUrl(context) + LOGIN2;

        JSONObject payload = new JSONObject();
        payload.put("Usuario", userName);
        payload.put("Contrasena", password);

        InputStream in =UtilsServices.getStreamPost(url, payload.toString());

        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String repartidorStr= gson.fromJson(reader, String.class);
        Repartidor repartidor = gson.fromJson(repartidorStr, Repartidor.class);
        return repartidor;
    }
}
