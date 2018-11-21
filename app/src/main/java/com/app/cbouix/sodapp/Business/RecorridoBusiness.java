package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Localidad;
import com.app.cbouix.sodapp.Models.Recorrido;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CBouix on 21/04/2017.
 */

public class RecorridoBusiness {
    private static final String RECORRIDO = "getRecorrido/%s,%s";
    private static final String LOCALIDADES = "getLocalidades";
    private static final String CREATE_RECORRIDO = "createTrayecto/%s";
    private static final String UPDATE_TRAYECTO = "updateTrayecto/%s";

    public static ArrayList<Recorrido> getRecorridos(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + RECORRIDO, DateTimeUtil.getDateNowString(),
                                        AppPreferences.getString(context,
                                                AppPreferences.KEY_REPARTIDOR, ""));
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String recorridosStr= gson.fromJson(reader, String.class);

        Recorrido[] recorridos = new Gson().fromJson(recorridosStr, Recorrido[].class);

        return new ArrayList<Recorrido>(Arrays.asList(recorridos));
    }

    public static ArrayList<Localidad> getLocalidades(Context context) throws IOException {
        String url = EnviromentManager.getUrl(context) + LOCALIDADES;
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String localidadesStr= gson.fromJson(reader, String.class);
        Localidad[] localidades = new Gson().fromJson(localidadesStr, Localidad[].class);

        return new ArrayList<Localidad>(Arrays.asList(localidades));
    }

    public static void createRecorrido(Context context, String descripcion, int orden) throws IOException, JSONException {
        String url = String.format(EnviromentManager.getUrl(context) + CREATE_RECORRIDO,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));

        JSONObject payload = new JSONObject();
        payload.put("CabeceraId", "");
        payload.put("LineaId", "");
        payload.put("Codigo", "");
        payload.put("Nombre", "");
        payload.put("Orden", orden);
        payload.put("Descripcion", descripcion);
        payload.put("ClienteCod", "");
        payload.put("ClienteNombre", "");
        payload.put("ClienteDomicilio", "");

        url = url.replace(" ", "%20");
        UtilsServices.getStreamPost(url, payload.toString());
    }

    public static void updateRecorrido(Context context, Recorrido recorrido)
                                                        throws IOException, JSONException {
        String url = String.format(EnviromentManager.getUrl(context) + UPDATE_TRAYECTO,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));

        JSONObject payload = new JSONObject();
        payload.put("CabeceraId", recorrido.getCabeceraId());
        payload.put("LineaId", recorrido.getLineaId());
        payload.put("Codigo", recorrido.getCodigo());
        payload.put("Nombre", recorrido.getNombre());
        payload.put("Orden", recorrido.getOrden());
        payload.put("Descripcion", recorrido.getDescripcion());
        payload.put("ClienteCod", recorrido.getClienteCod());
        payload.put("ClienteNombre", recorrido.getClienteNombre());
        payload.put("ClienteDomicilio", recorrido.getClienteDomicilio());

        url = url.replace(" ", "%20");
        UtilsServices.getStreamPut(url, payload.toString());
    }
}
