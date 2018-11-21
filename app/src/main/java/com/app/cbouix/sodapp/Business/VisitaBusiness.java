package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.Models.TipoDeVisita;
import com.app.cbouix.sodapp.Models.Visita;
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
 * Created by CBouix on 20/05/2017.
 */

public class VisitaBusiness {
    private static final String GET_TIPO_VISITA = "getTiposVisita";
    private static final String GET_VISITAS = "getVisitas/%s,%s,%s";
    private static final String CREATE_VISITA = "createVisita/%s";
    private static final String GET_VISITA_TIPO = "getVisitasPorTipo/%s,%s,%s";

    public static ArrayList<TipoDeVisita> getTiposDeVisitas(Context context) throws IOException {
        String url = EnviromentManager.getUrl(context) + GET_TIPO_VISITA;

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String tipoVisitasStr= gson.fromJson(reader, String.class);
        TipoDeVisita[] tipoDeVisitas = new Gson().fromJson(tipoVisitasStr, TipoDeVisita[].class);

        return new ArrayList<TipoDeVisita>(Arrays.asList(tipoDeVisitas));
    }

    public static ArrayList<Visita> getVisitas(Context context, int clienteId) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + GET_VISITAS,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""), clienteId,
                DateTimeUtil.getDateNowString());

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String visitasStr= gson.fromJson(reader, String.class);
        Visita[] visitas = new Gson().fromJson(visitasStr, Visita[].class);

        return new ArrayList<Visita>(Arrays.asList(visitas));
    }

    public static ArrayList<Visita> getVisitasPorTipo(Context context, String tipo) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + GET_VISITA_TIPO,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""), tipo,
                DateTimeUtil.getDateNowString());

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String visitasStr= gson.fromJson(reader, String.class);
        Visita[] visitas = new Gson().fromJson(visitasStr, Visita[].class);

        return new ArrayList<Visita>(Arrays.asList(visitas));
    }

    public static void createVisita(Context context, Cliente cliente, Domicilio domicilio, TipoDeVisita tipoDeVisita) throws IOException, JSONException {

        JSONObject payload = new JSONObject();
        payload.put("VisitaId", 0);
        payload.put("Numero", 0);
        payload.put("FechaEmision", DateTimeUtil.getDateNowString());
        payload.put("Descripcion", "");
        payload.put("ClienteId", cliente.getId());
        payload.put("ClienteCod", cliente.getCodigo());
        payload.put("ClienteNombre", cliente.getNombre());
        payload.put("DomicilioId", domicilio.getDomicilioId());
        payload.put("Direccion", domicilio.getDireccion());
        payload.put("RepartidorId", AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));
        payload.put("RepartidorCod", AppPreferences.getString(context, AppPreferences.KEY_COD_REPARTIDOR, ""));
        payload.put("RepartidorNombre", AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        payload.put("TipoVisitaId", tipoDeVisita.getId());
        payload.put("TipoVisitaCod", tipoDeVisita.getCod());
        payload.put("TipoVisitaNombre", tipoDeVisita.getMotivo());

        String url = String.format(EnviromentManager.getUrl(context) + CREATE_VISITA,
                AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        url = url.replace(" ", "%20");
        UtilsServices.getStreamPost(url, payload.toString());
    }
}
