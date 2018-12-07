package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.ReportesDia.ReporteCliente;
import com.app.cbouix.sodapp.Models.ReportesDia.Resumen;
import com.app.cbouix.sodapp.Models.ReportesDia.VentaArticulo;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
import com.app.cbouix.sodapp.Utils.DateTimeUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;

public class ReporteBusines {

    private static final String RESUMEN_DEL_DIA = "getResumenDelDia/%s,%s";
    private static final String REPORTE_DEL_DIA = "getReporteArticDelDia/%s,%s";
    private static final String CLIENTE_ARTILCULO_DEL_DIA = "getReporteClienteArticDelDia/%s,%s,%s";
    private static final String REPORTE_CLIENTE_VENTA_DEL_DIA = "getReporteClienteVentaDelDia/%s,%s";
    private static final String REPORTE_CLIENTE_COBRANZA_DEL_DIA = "getReporteClienteCobranzaDelDia/%s,%s";
    private static final String REPORTE_CLIENTE_NO_COMPRARON = "getReporteClienteNoCompraron/%s,%s";


    public static Resumen getResumenDelDia(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + RESUMEN_DEL_DIA,
                AppPreferences.getString(context,
                        AppPreferences.KEY_REPARTIDOR, ""),
                DateTimeUtil.getDateNowString());
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String resumenStr = gson.fromJson(reader, String.class);

        Resumen resumenDelDia = new Gson().fromJson(resumenStr, Resumen.class);

        return resumenDelDia;
    }

    public static ArrayList<VentaArticulo> getReportesDelDia(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + REPORTE_DEL_DIA,
                AppPreferences.getString(context,
                        AppPreferences.KEY_REPARTIDOR, ""),
                DateTimeUtil.getDateNowString());
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String resumenStr = gson.fromJson(reader, String.class);

        VentaArticulo[] reportesDelDia = new Gson().fromJson(resumenStr, VentaArticulo[].class);

        return new ArrayList<VentaArticulo>(Arrays.asList(reportesDelDia));
    }

    public static ArrayList<ReporteCliente> getClienteArticulo(Context context, int articuloId) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + CLIENTE_ARTILCULO_DEL_DIA, DateTimeUtil.getDateNowString(),
                AppPreferences.getString(context,
                        AppPreferences.KEY_REPARTIDOR, ""), articuloId);
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String clienteArticuloStr = gson.fromJson(reader, String.class);

        ReporteCliente[] clienteArticulo = new Gson().fromJson(clienteArticuloStr, ReporteCliente[].class);

        return new ArrayList<ReporteCliente>(Arrays.asList(clienteArticulo));

    }

    public static ArrayList<ReporteCliente> getReporteClienteVenta(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + REPORTE_CLIENTE_VENTA_DEL_DIA,
                AppPreferences.getString(context,
                        AppPreferences.KEY_REPARTIDOR, ""),
                DateTimeUtil.getDateNowString());
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String resumenStr= gson.fromJson(reader, String.class);

        ReporteCliente[] clienteVentas = new Gson().fromJson(resumenStr, ReporteCliente[].class);

        return new ArrayList<ReporteCliente>(Arrays.asList(clienteVentas));
    }

    public static ArrayList<ReporteCliente> getReporteClienteCobranza(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + REPORTE_CLIENTE_COBRANZA_DEL_DIA,
                AppPreferences.getString(context,
                        AppPreferences.KEY_REPARTIDOR, ""),
                DateTimeUtil.getDateNowString());
        InputStream in = UtilsServices.getStream(url);

        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String resumenStr= gson.fromJson(reader, String.class);

        ReporteCliente[] clienteCobranzas = new Gson().fromJson(resumenStr, ReporteCliente[].class);

        return new ArrayList<ReporteCliente>(Arrays.asList(clienteCobranzas));
    }

    public static ArrayList<ReporteCliente> getReporteClienteNoCompraron(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + REPORTE_CLIENTE_NO_COMPRARON,
                AppPreferences.getString(context,
                        AppPreferences.KEY_REPARTIDOR, ""),
                DateTimeUtil.getDateNowString());
        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String resumenStr= gson.fromJson(reader, String.class);

        ReporteCliente[] clienteNoCompraron = new Gson().fromJson(resumenStr, ReporteCliente[].class);

        return new ArrayList<ReporteCliente>(Arrays.asList(clienteNoCompraron));
    }

}
