package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.Models.Banco;
import com.app.cbouix.sodapp.Models.MedioDePago;
import com.app.cbouix.sodapp.Models.Tarjeta;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CBouix on 21/04/2017.
 */

public class MediosDePagosBusiness {
    private static final String BANCO = "getBancos";
    private static final String MEDIOS_DE_PAGO_CZA = "getMediosPagoCza";
    private static final String TARJETA_CREDITO = "getTarjetasCredito";
    private static final String TARJETA_DEBITO = "getTarjetasDebito";


    public static final int TARJETA_ID_CREDITO = 1;
    public static final int TARJETA_ID_DEBITO = 2;

    public static ArrayList<Banco> getBancos(Context context) throws IOException {
        String url = EnviromentManager.getUrl(context) + BANCO;
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String bancoStr= gson.fromJson(reader, String.class);
        Banco[] bancos = new Gson().fromJson(bancoStr, Banco[].class);

        return new ArrayList<Banco>(Arrays.asList(bancos));
    }

    public static ArrayList<MedioDePago> getMediosDePagos(Context context) throws IOException {
        String url = EnviromentManager.getUrl(context) + MEDIOS_DE_PAGO_CZA;

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String chequeStr= gson.fromJson(reader, String.class);
        MedioDePago[] cheques = new Gson().fromJson(chequeStr, MedioDePago[].class);

        return new ArrayList<MedioDePago>(Arrays.asList(cheques));
    }

    public static ArrayList<Tarjeta> getTarjetas(Context context, int tipoTarjeta) throws IOException {
        String url;
        if(tipoTarjeta == TARJETA_ID_CREDITO){
            url = EnviromentManager.getUrl(context) + TARJETA_CREDITO;
        }else{
            url = EnviromentManager.getUrl(context) + TARJETA_DEBITO;
        }

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String tarjetaStr= gson.fromJson(reader, String.class);
        Tarjeta[] tarjetas = new Gson().fromJson(tarjetaStr, Tarjeta[].class);

        return new ArrayList<Tarjeta>(Arrays.asList(tarjetas));
    }
}
