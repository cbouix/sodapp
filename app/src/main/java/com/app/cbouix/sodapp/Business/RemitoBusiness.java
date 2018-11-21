package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.DataBaseManager;
import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.DataAccess.DataBase.Cobranza;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.CobranzaLinDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoDao;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLin;
import com.app.cbouix.sodapp.DataAccess.DataBase.RemitoLinDao;
import com.app.cbouix.sodapp.Exceptions.RemitoExeption;
import com.app.cbouix.sodapp.Models.Json.JsonRemito;
import com.app.cbouix.sodapp.Models.Responce;
import com.app.cbouix.sodapp.Models.Remito;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
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
 * Created by CBouix on 21/04/2017.
 */

public class RemitoBusiness {
    private static final String REMITOS = "getRemitosCab/%s,%s,%s";
    private static final String CREATE_REMITO = "createRemito/%s";
    private static final String GET_REMITOS = "getRemitoById/%s";
    private static final String UPDATE_REMITO = "updateRemito/%s";

    public static ArrayList<Remito> getRemitos(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + REMITOS,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""), 0, DateTimeUtil.getDateNowString());

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String remitosStr= gson.fromJson(reader, String.class);
        Remito[] remitos = new Gson().fromJson(remitosStr, Remito[].class);

        return new ArrayList<Remito>(Arrays.asList(remitos));
    }

    public static JsonRemito getRemitoById(Context context, int remitoId) throws
                                                        IOException, RemitoExeption, JSONException {
        String url = String.format(EnviromentManager.getUrl(context) + GET_REMITOS, remitoId);

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String remitosStr= gson.fromJson(reader, String.class);

        JSONObject filedata = new JSONObject(remitosStr);

        JSONObject remitoCabJson = (JSONObject) filedata.get("remitoCab");
        com.app.cbouix.sodapp.Models.Json.RemitoCab remitosCab = new Gson().fromJson(remitoCabJson.toString(), com.app.cbouix.sodapp.Models.Json.RemitoCab.class);

        String remitosLinJson = (String) filedata.get("remitoLin");
        com.app.cbouix.sodapp.Models.Json.RemitoLin[] remitosLin = new Gson().fromJson(remitosLinJson, com.app.cbouix.sodapp.Models.Json.RemitoLin[].class);
        List<com.app.cbouix.sodapp.Models.Json.RemitoLin> remitosLinList = new ArrayList<com.app.cbouix.sodapp.Models.Json.RemitoLin>(Arrays.asList(remitosLin));

        JSONObject cobranzaCabJson = (JSONObject) filedata.get("cobranzaCab");
        com.app.cbouix.sodapp.Models.Json.CobranzaCab cobranzaCab = new Gson().fromJson(cobranzaCabJson.toString(), com.app.cbouix.sodapp.Models.Json.CobranzaCab.class);

        Object cobranzaLinObj = filedata.get("cobranzaLin");
        JsonRemito jsonRemito = new JsonRemito();
        jsonRemito.setRemitoCab(remitosCab);
        jsonRemito.setRemitoLin(remitosLinList);
        jsonRemito.setCobranzaCab(cobranzaCab);
        List<com.app.cbouix.sodapp.Models.Json.CobranzaLin> cobranzaLinList = new ArrayList<>();
        if(!cobranzaLinObj.equals(null)){
            String cobranzaLinJson = (String) cobranzaLinObj;
            com.app.cbouix.sodapp.Models.Json.CobranzaLin[] cobranzaLin = new Gson().fromJson(cobranzaLinJson, com.app.cbouix.sodapp.Models.Json.CobranzaLin[].class);
            cobranzaLinList = new ArrayList<com.app.cbouix.sodapp.Models.Json.CobranzaLin>(Arrays.asList(cobranzaLin));
        }
        jsonRemito.setCobranzaLin(cobranzaLinList);

        return jsonRemito;
    }

    public static void createRemito(Context context, long remitoId, long cobranzaId) throws
                                                        IOException, RemitoExeption {

        String repartidoId = AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, "");
        String repartidoNombre = AppPreferences.getString(context, AppPreferences.KEY_USUARIO, "");

        //Creo los Dao de Remito, RemitoLin, Cobranza y CobranzaLin para obtener los datos
        RemitoDao remitoDao = DataBaseManager.getInstance().getDaoSession().getRemitoDao();
        RemitoLinDao remitoLinDao = DataBaseManager.getInstance().getDaoSession().getRemitoLinDao();
        CobranzaDao cobranzaDao = DataBaseManager.getInstance().getDaoSession().getCobranzaDao();
        CobranzaLinDao cobranzaLinDao = DataBaseManager.getInstance().getDaoSession().getCobranzaLinDao();

        //Obtengo el Remito y la Cobranza Cabecera de la base de datos
        com.app.cbouix.sodapp.DataAccess.DataBase.Remito remito = remitoDao.load(remitoId);
        remito.setImporte(Double.parseDouble(AppPreferences.getString(context, AppPreferences.KEY_IMPORTE_TOTAL,"" )));
        Cobranza cobranza = cobranzaDao.load(cobranzaId);
        cobranza.setImporte(Double.parseDouble(AppPreferences.getString(context, AppPreferences.KEY_IMPORTE_PARCIAL,"" )));

        //Obtengo la lista de remitosLin de la base de datos
        QueryBuilder queryBuilderRemito = remitoLinDao.queryBuilder().where
                (RemitoLinDao.Properties.RemitoId.eq(remitoId));
        List<RemitoLin> listRemitosLin = queryBuilderRemito.list();

        //Obtengo la lista de cobranzaLin de la base de datos
        QueryBuilder queryBuilderCobranza = cobranzaLinDao.queryBuilder().where
                (CobranzaLinDao.Properties.CobranzaId.eq(cobranzaId));
        List<CobranzaLin> listCobranzaLin = queryBuilderCobranza.list();


        //////////////////////////////////////////////////
        //Obtengo los datos del remito a actualizar
        int remitoUpdate= AppPreferences.getInt(context, AppPreferences.KEY_REMITO_EDIT_ID, 0);
        int cobranzaUpdate= AppPreferences.getInt(context, AppPreferences.KEY_COBRANZA_EDIT_ID, 0);
        int versionRemito= AppPreferences.getInt(context, AppPreferences.VERSION_REMITO, 0);
        int versionCobranza= AppPreferences.getInt(context, AppPreferences.VERSION_COBRANZA,0);

        //Inicializo el json con los datos de Remito y cobranza
        //REMITO
        //Remito Cabecera
        JsonObject innerRemitoCab = new JsonObject();
        Long cabeceraId = (remitoUpdate > 0) ? remitoUpdate : remito.getId();
        int versionR = (versionRemito>0) ? versionRemito : 1;
        innerRemitoCab.addProperty("CabeceraId", cabeceraId);
        innerRemitoCab.addProperty("Emision", remito.getEmision());
        innerRemitoCab.addProperty("Numero", remito.getNumero());
        innerRemitoCab.addProperty("Importe", remito.getImporte());
        innerRemitoCab.addProperty("ClienteId", remito.getClienteId());
        innerRemitoCab.addProperty("ClienteCod", remito.getClienteCod());
        innerRemitoCab.addProperty("ClienteNombre", remito.getClienteNombre());
        innerRemitoCab.addProperty("ListaPrecioId", remito.getListPrecioId());
        innerRemitoCab.addProperty("DomicilioId", remito.getDomicilioId());
        innerRemitoCab.addProperty("Direccion", remito.getDireccion());
        innerRemitoCab.addProperty("RepartidorId", repartidoId);
        innerRemitoCab.addProperty("Version", versionR);

        //remitoLin List
        String jsonRemitosLin = new Gson().toJson(listRemitosLin);

        //Creo el Json REMITO
        JsonObject jsonRemito = new JsonObject();
        jsonRemito.add("RemitoCab", innerRemitoCab);
        jsonRemito.addProperty("RemitoLin", jsonRemitosLin);

        //Si no pago, la lista de cobranzaLin es == 0, elimina la cabecera de cobranza
        if(listCobranzaLin.size() > 0){
            //COBRANZA
            //Cobranza Cabecera
            JsonObject innerCobranzaCab = new JsonObject();

            Long cabeceraIdCob = (cobranzaUpdate > 0) ? cobranzaUpdate : cobranza.getId();
            int versionCob = (versionCobranza>0) ? versionCobranza : 1;

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

            jsonRemito.add("CobranzaCab", innerCobranzaCab);
            jsonRemito.addProperty("CobranzaLin", jsonCobranzaLin);
        }
        String url;
        InputStream in;
        if(remitoUpdate > 0){
            url = String.format(EnviromentManager.getUrl(context) + UPDATE_REMITO, repartidoNombre);
            in = UtilsServices.getStreamPut(url, jsonRemito.toString());
            AppPreferences.setString(context, AppPreferences.KEY_REMITO_EDIT, "");

            //BORRO EL REMITO Y LA COBRANZA
            //ELIMINO LAS CLAVES
            AppPreferences.setLong(context,
                    AppPreferences.KEY_REMITO, 0);
            AppPreferences.setLong(context,
                    AppPreferences.KEY_COBRANZA, 0);
            AppPreferences.setLong(context,
                    AppPreferences.KEY_CLIENTE, 0);

            remitoDao.delete(remito);
            for (RemitoLin item: listRemitosLin) {
                remitoLinDao.delete(item);
            }
            cobranzaDao.delete(cobranza);
            for (CobranzaLin item: listCobranzaLin) {
                cobranzaLinDao.delete(item);
            }

            AppPreferences.setInt(context, AppPreferences.KEY_REMITO_EDIT_ID, 0);
            AppPreferences.setInt(context, AppPreferences.KEY_COBRANZA_EDIT_ID, 0);
            AppPreferences.setInt(context, AppPreferences.VERSION_REMITO, 0);
            AppPreferences.setInt(context, AppPreferences.VERSION_COBRANZA,0);

        }else {
            url = String.format(EnviromentManager.getUrl(context) + CREATE_REMITO, repartidoNombre);
            in = UtilsServices.getStreamPost(url, jsonRemito.toString());

            Gson gson = new Gson();
            Reader reader = new InputStreamReader(in, "UTF-8");
            String responceStr = gson.fromJson(reader, String.class);
            Responce responce = new Gson().fromJson(responceStr, Responce.class);

            if(responce.hasError()){
                throw new RemitoExeption(responce.getError());
            }else{
                //BORRO EL REMITO Y LA COBRANZA
                //ELIMINO LAS CLAVES
                AppPreferences.setLong(context,
                        AppPreferences.KEY_REMITO, 0);
                AppPreferences.setLong(context,
                        AppPreferences.KEY_COBRANZA, 0);
                AppPreferences.setLong(context,
                        AppPreferences.KEY_CLIENTE, 0);

                remitoDao.delete(remito);
                for (RemitoLin item: listRemitosLin) {
                    remitoLinDao.delete(item);
                }
                cobranzaDao.delete(cobranza);
                for (CobranzaLin item: listCobranzaLin) {
                    cobranzaLinDao.delete(item);
                }
            }
        }
    }
}
