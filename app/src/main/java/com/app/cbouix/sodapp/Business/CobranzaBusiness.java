package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cobranza;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLinDao;
import com.app.cbouix.sodapp.Exceptions.RemitoExeption;
import com.app.cbouix.sodapp.Models.Json.JsonCobranza;
import com.app.cbouix.sodapp.Models.Responce;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by CBouix on 13/05/2017.
 */

public class CobranzaBusiness {

    private static final String COBRANZAS = "getCobranzasCab/%s,%s,%s";
    private static final String CREATE_COBRANZA = "createCobranza/%s";
    private static final String GET_COBRANZAS = "getCobranzaById/%s";
    private static final String UPDATE_COBRANZA = "updateCobranza/%s";

    public static ArrayList<com.app.cbouix.sodapp.Models.Cobranza> getCobranzas(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + COBRANZAS,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""), 0, DateTimeUtil.getDateNowString());

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String cobranzasStr= gson.fromJson(reader, String.class);
        com.app.cbouix.sodapp.Models.Cobranza[] cobranzas = new Gson().fromJson(cobranzasStr, com.app.cbouix.sodapp.Models.Cobranza[].class);

        return new ArrayList<com.app.cbouix.sodapp.Models.Cobranza>(Arrays.asList(cobranzas));
    }

    public static JsonCobranza getCobranzaById(Context context, long cobranzaId) throws
            IOException, RemitoExeption {
        String url = String.format(EnviromentManager.getUrl(context) + GET_COBRANZAS, cobranzaId);

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String cobranzaStr= gson.fromJson(reader, String.class);
        try{
            JSONObject filedata = new JSONObject(cobranzaStr);

            JSONObject cobranzaCabJson = (JSONObject) filedata.get("cobranzaCab");
            com.app.cbouix.sodapp.Models.Json.CobranzaCab cobranzaCab = new Gson().fromJson(cobranzaCabJson.toString(), com.app.cbouix.sodapp.Models.Json.CobranzaCab.class);

            String cobranzaLinJson = (String) filedata.get("cobranzaLin");
            com.app.cbouix.sodapp.Models.Json.CobranzaLin[] cobranzaLin = new Gson().fromJson(cobranzaLinJson, com.app.cbouix.sodapp.Models.Json.CobranzaLin[].class);
            List<com.app.cbouix.sodapp.Models.Json.CobranzaLin> cobranzaLinList = new ArrayList<com.app.cbouix.sodapp.Models.Json.CobranzaLin>(Arrays.asList(cobranzaLin));

            JsonCobranza jsonCobranza = new JsonCobranza();
            jsonCobranza.setCobranzaCab(cobranzaCab);
            jsonCobranza.setCobranzaLin(cobranzaLinList);

            return jsonCobranza;
        }catch (Exception e){

        }

        return null;
    }

    public static void createCobranza(Context context, long cobranzaId) throws
            IOException, RemitoExeption {

        String repartidoId = AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, "");
        String repartidoNombre = AppPreferences.getString(context, AppPreferences.KEY_USUARIO, "");

        CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();
        CobranzaLinDao cobranzaLinDao = DataBaseManager.getInstance().getDaoSession().getCobranzaLinDao();

        //Obtengo la Cobranza Cabecera de la base de datos
        Cobranza cobranza = cobranzaDao.load(cobranzaId);
        cobranza.setImporte(Double.parseDouble(AppPreferences.getString(context, AppPreferences.KEY_IMPORTE_PARCIAL,"" )));

        //Obtengo la lista de cobranzaLin de la base de datos
        QueryBuilder queryBuilderCobranza = cobranzaLinDao.queryBuilder().where
                (CobranzaLinDao.Properties.CobranzaId.eq(cobranzaId));
        List<CobranzaLin> listCobranzaLin = queryBuilderCobranza.list();

        //////////////////////////////////////////////////
        //Obtengo los datos del remito a actualizar
        int cobranzaUpdate= AppPreferences.getInt(context, AppPreferences.KEY_COBRANZA_EDIT_ID, 0);
        int versionCobranza= AppPreferences.getInt(context, AppPreferences.VERSION_COBRANZA,0);

        //Inicializo el json con los datos de cobranza

        Long cabeceraIdCob = (cobranzaUpdate > 0) ? cobranzaUpdate : cobranza.getId();
        int versionCob = (versionCobranza>0) ? versionCobranza : 1;

        //COBRANZA
        //Cobranza Cabecera
        JsonObject innerCobranzaCab = new JsonObject();
        innerCobranzaCab.addProperty("CabeceraId", cabeceraIdCob);
        innerCobranzaCab.addProperty("Emision", cobranza.getEmision());
        innerCobranzaCab.addProperty("Numero", cobranza.getNumero());
        innerCobranzaCab.addProperty("Importe", cobranza.getImporte());
        innerCobranzaCab.addProperty("ClienteId", cobranza.getClienteId());
        innerCobranzaCab.addProperty("ClienteCod", cobranza.getClienteCod());
        innerCobranzaCab.addProperty("ClienteNombre", cobranza.getClienteNombre());
        innerCobranzaCab.addProperty("DomicilioId", cobranza.getDomicilioId());
        innerCobranzaCab.addProperty("Direccion", cobranza.getDomicilio());
        innerCobranzaCab.addProperty("RepartidorId", repartidoId);
        innerCobranzaCab.addProperty("Version", versionCob);

        //CobranzaLin List
        String jsonCobranzaLin = new Gson().toJson(listCobranzaLin);

        //Creo el Json REMITO
        JsonObject jsonRemito = new JsonObject();
        jsonRemito.add("CobranzaCab", innerCobranzaCab);
        jsonRemito.addProperty("CobranzaLin", jsonCobranzaLin);

        String url;
        InputStream in;

        if(cobranzaUpdate > 0)
        {
            url = String.format(EnviromentManager.getUrl(context) + UPDATE_COBRANZA, repartidoNombre);
            in = UtilsServices.getStreamPut(url, jsonRemito.toString());

            AppPreferences.setString(context, AppPreferences.KEY_REMITO_EDIT, "");

            //BORRO LA COBRANZA
            //ELIMINO LAS CLAVES
            AppPreferences.setLong(context,
                    AppPreferences.KEY_COBRANZA, 0);
            AppPreferences.setLong(context,
                    AppPreferences.KEY_CLIENTE, 0);

            cobranzaDao.delete(cobranza);
            for (CobranzaLin item: listCobranzaLin) {
                cobranzaLinDao.delete(item);
            }

            AppPreferences.setInt(context, AppPreferences.KEY_COBRANZA_EDIT_ID, 0);
            AppPreferences.setInt(context, AppPreferences.VERSION_COBRANZA,0);
        }
        else
        {
            url = String.format(EnviromentManager.getUrl(context) + CREATE_COBRANZA, repartidoNombre);
            in = UtilsServices.getStreamPost(url, jsonRemito.toString());

            Gson gson = new Gson();
            Reader reader = new InputStreamReader(in, "UTF-8");
            String responceStr = gson.fromJson(reader, String.class);
            Responce responce = new Gson().fromJson(responceStr, Responce.class);

            if(responce.hasError()){
                throw new RemitoExeption("Error al crear la cobranza.");
            }else{
                //BORRO LA COBRANZA
                //ELIMINO LAS CLAVES
                AppPreferences.setLong(context,
                        AppPreferences.KEY_COBRANZA, 0);
                AppPreferences.setLong(context,
                        AppPreferences.KEY_CLIENTE, 0);

                cobranzaDao.delete(cobranza);
                for (CobranzaLin item: listCobranzaLin) {
                    cobranzaLinDao.delete(item);
                }
            }
        }
    }
}
