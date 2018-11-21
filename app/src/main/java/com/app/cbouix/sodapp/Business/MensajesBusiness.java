package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.Models.Mensaje;
import com.app.cbouix.sodapp.Models.Novedad;
import com.app.cbouix.sodapp.Models.Repartidor;
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
 * Created by CBouix on 28/04/2017.
 */

public class MensajesBusiness {

    private static final String CREATE_MENSAJE = "createMensaje/%s";
    private static final String CREATE_NOVEDAD = "createNovedad/%s";
    private static final String REPARTIDORES = "getRepartidores/%s,Si";
    public static final String MENSAJES = "getMensajes/%s,%s";
    public static final String NOVEDADES = "getNovedades/%s,%s,%s";
    public static final String UPDATE_MENSAJE_LECTURA = "updateMensajeLectura/%s";

    public static void sendNovedad(Context context, String mensaje, Cliente cliente, Domicilio domicilio) throws IOException, JSONException {

        JSONObject payload = new JSONObject();
        payload.put("Id", 0);
        payload.put("Numero", 0);
        payload.put("FechaEmision", DateTimeUtil.getDateNowString());
        payload.put("Descripcion", mensaje);
        payload.put("ClienteId", cliente.getId());
        payload.put("ClienteCod", cliente.getCodigo());
        payload.put("ClienteNombre", cliente.getNombre());
        payload.put("DomicilioId", domicilio.getDomicilioId());
        payload.put("Direccion", domicilio.getDireccion());
        payload.put("RepartidorId", AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));
        payload.put("RepartidorCod", AppPreferences.getString(context, AppPreferences.KEY_COD_REPARTIDOR, ""));
        payload.put("RepartidorNombre", AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));

        String url = String.format(EnviromentManager.getUrl(context) + CREATE_NOVEDAD,
                AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        url = url.replace(" ", "%20");
        UtilsServices.getStreamPost(url, payload.toString());
    }

    public static void sendMensaje(Context context, String mensaje, Repartidor destinatario) throws IOException, JSONException {
        JSONObject payload = new JSONObject();
        payload.put("Id", 0);
        payload.put("Numero", 0);
        payload.put("TextoMensaje", mensaje);
        payload.put("RepdorCreacionId", AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));
        payload.put("RepdorCreacionCod", AppPreferences.getString(context, AppPreferences.KEY_COD_REPARTIDOR, ""));
        payload.put("RepdorCreacionNombre", AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        payload.put("FechaCreacion", DateTimeUtil.getDateNowString());
        payload.put("LoginCreacion", "");
        payload.put("NombreCreacion", "");
        payload.put("RepdorLecturaId", "");
        payload.put("RepdorLecturaCod", "");
        payload.put("RepdorLecturaNombre", "");
        payload.put("FechaLectura", "");
        payload.put("LoginLectura", "");
        payload.put("NombreLectura", "");
        payload.put("RepdorDestinoId", destinatario.getId());
        payload.put("RepdorDestinoCod", destinatario.getCodigo());
        payload.put("RepdorDestinoNombre", destinatario.getNombre());
        payload.put("LoginDestino", "");
        payload.put("NombreDestino", "");
        payload.put("GCMRegId", destinatario.getgCMRegId());

        String url = String.format(EnviromentManager.getUrl(context) + CREATE_MENSAJE,
                AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));

        url = url.replace(" ", "%20");
        UtilsServices.getStreamPost(url, payload.toString());
    }

    public static void updateLeido(Context context, Mensaje mensaje) throws IOException, JSONException {

        JSONObject payload = new JSONObject();
        payload.put("Id", mensaje.getId());
        payload.put("Numero", mensaje.getNumero());
        payload.put("TextoMensaje", mensaje.getTextoMensaje());
        payload.put("RepdorCreacionId", mensaje.getRepdorCreacionId());
        payload.put("RepdorCreacionCod", mensaje.getRepdorCreacionCod());
        payload.put("RepdorCreacionNombre", mensaje.getRepdorCreacionNombre());
        payload.put("FechaCreacion", mensaje.getFechaCreacion());
        payload.put("LoginCreacion", mensaje.getLoginCreacion());
        payload.put("NombreCreacion", mensaje.getNombreCreacion());
        payload.put("RepdorLecturaId", AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));
        payload.put("RepdorLecturaCod", AppPreferences.getString(context, AppPreferences.KEY_COD_REPARTIDOR, ""));
        payload.put("RepdorLecturaNombre", AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        payload.put("FechaLectura", DateTimeUtil.getDateNowStringWithMinutes());
        payload.put("LoginLectura", AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        payload.put("NombreLectura", mensaje.getNombreLectura());
        payload.put("RepdorDestinoId", mensaje.getRepdorDestinoId());
        payload.put("RepdorDestinoCod", mensaje.getRepdorDestinoCod());
        payload.put("RepdorDestinoNombre", mensaje.getRepdorDestinoNombre());
        payload.put("LoginDestino", mensaje.getLoginDestino());
        payload.put("NombreDestino", mensaje.getNombreDestino());
        payload.put("GCMRegId", mensaje.getGcmRegId());

        String url = String.format(EnviromentManager.getUrl(context) + UPDATE_MENSAJE_LECTURA,
                AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));
        url = url.replace(" ", "%20");
        UtilsServices.getStreamPut(url, payload.toString());
    }

    public static ArrayList<Repartidor> getRepartidores(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + REPARTIDORES,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String repartidoresStr= gson.fromJson(reader, String.class);
        Repartidor[] repartidores = new Gson().fromJson(repartidoresStr, Repartidor[].class);

        return new ArrayList<Repartidor>(Arrays.asList(repartidores));
    }

    public static ArrayList<Mensaje> getMensajes(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + MENSAJES,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""), DateTimeUtil.getDateNowString());

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String mensajesStr= gson.fromJson(reader, String.class);
        Mensaje[] mensajes = new Gson().fromJson(mensajesStr, Mensaje[].class);

        return new ArrayList<Mensaje>(Arrays.asList(mensajes));
    }

    public static ArrayList<Novedad> getNovedades(Context context, int clienteId) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + NOVEDADES,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""),
                clienteId, DateTimeUtil.getDateNowString());

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String novedadesStr= gson.fromJson(reader, String.class);
        Novedad[] novedades = new Gson().fromJson(novedadesStr, Novedad[].class);

        return new ArrayList<Novedad>(Arrays.asList(novedades));
    }
}
