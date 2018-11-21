package com.app.cbouix.sodapp.Business;

import android.content.Context;

import com.app.cbouix.sodapp.DataAccess.DataAccess.Preferences.AppPreferences;
import com.app.cbouix.sodapp.Models.Cliente;
import com.app.cbouix.sodapp.Models.ClienteHistorial;
import com.app.cbouix.sodapp.Models.Domicilio;
import com.app.cbouix.sodapp.Models.Stock;
import com.app.cbouix.sodapp.Services.EnviromentManager;
import com.app.cbouix.sodapp.Services.UtilsServices;
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
 * Created by CBouix on 17/04/2017.
 */

public class ClientesBusiness {
    private static final String GET_CLIENTE = "getClienteById/%s";
    private static final String CLIENTES = "getClientesDelRepartidor/%s,0";
    private static final String CREATE_CLIENTE = "createCliente/%s";
    private static final String STOCK_CLIENTE = "getStockCliente/%s";
    private static final String DOMICILIO_CLIENTES = "getDomiciliosCliente/%s,%s";
    private static final String UPDATE_CLIENTE = "updateCliente/%s";
    private static final String HISTORIAL_CLIENTE = "getHistoriaCliente/%s";

    public static Cliente getCliente(Context context, String clienteId) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + GET_CLIENTE, clienteId);

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String clienteStr= gson.fromJson(reader, String.class);
        Cliente cliente = gson.fromJson(clienteStr, Cliente.class);
        return cliente;
    }

    public static ArrayList<Cliente> getClientes(Context context) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + CLIENTES, AppPreferences.getString(context,
                AppPreferences.KEY_REPARTIDOR, ""));

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String clienteStr= gson.fromJson(reader, String.class);
        Cliente[] clientes = new Gson().fromJson(clienteStr, Cliente[].class);

        return new ArrayList<Cliente>(Arrays.asList(clientes));
    }

    public static void createNewCliente(Context context, String nombre, String direccion, int localidadId,
                                            int telefono, int telCelular, String mail) throws IOException, JSONException {

        String url = String.format(EnviromentManager.getUrl(context) + CREATE_CLIENTE,
                AppPreferences.getString(context, AppPreferences.KEY_USUARIO, ""));

        JSONObject payload = new JSONObject();
        payload.put("ID", 0);
        payload.put("Codigo", "");
        payload.put("NombreCorto", "");
        payload.put("Nombre", nombre);
        payload.put("TipoDocumentoCod", "");
        payload.put("NroDocumento", "");
        payload.put("SitFiscalId", "");
        payload.put("CUIT", "");
        payload.put("CategoriaId", "");
        payload.put("ListaPrecioId", "");
        payload.put("CondicionVentaId", "");
        payload.put("ZonaId", "");
        payload.put("PorcDescuento", "0");
        payload.put("PorcRecargo", "0");
        payload.put("PorcComiVta", "0");
        payload.put("Activo", "Si");
        payload.put("Saldo", "0");
        payload.put("TipoClienteCod", "");
        payload.put("Sector", "1");
        payload.put("DomicilioId", "");
        payload.put("Direccion", direccion);
        payload.put("Telefono", telefono);
        payload.put("TelCelular", telCelular == 0 ? "": telCelular);
        payload.put("eMail", mail);
        payload.put("LocalidadId", localidadId);
        payload.put("ProvinciaId", "");
        payload.put("Version", "1");

        url = url.replace(" ", "%20");
        UtilsServices.getStreamPost(url, payload.toString());
    }

    public static void updateCliente(Context context, Cliente cliente) throws IOException, JSONException {

        String url = String.format(EnviromentManager.getUrl(context) + UPDATE_CLIENTE,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));

        JSONObject payload = new JSONObject();
        payload.put("ID", cliente.getId());
        payload.put("Codigo", cliente.getCodigo());
        payload.put("NombreCorto", cliente.getNombreCorto());
        payload.put("Nombre", cliente.getNombre());
        payload.put("TipoDocumentoCod", cliente.getTipoDocumentoCod());
        payload.put("NroDocumento", cliente.getNroDocumento());
        payload.put("SitFiscalId", cliente.getSisFiscalId());
        payload.put("CUIT", cliente.getCUIT());
        payload.put("CategoriaId", cliente.getCategoriaId());
        payload.put("ListaPrecioId", cliente.getListPrecioId());
        payload.put("CondicionVentaId", cliente.getCondicionVentaId());
        payload.put("ZonaId", cliente.getZonaId());
        payload.put("PorcDescuento", cliente.getPorcDescuento());
        payload.put("PorcRecargo", cliente.getPorcRecargo());
        payload.put("PorcComiVta", cliente.getPorcComVta());
        payload.put("Activo", cliente.getActivo());
        payload.put("Saldo", cliente.getSaldo());
        payload.put("TipoClienteCod", cliente.getTipoClienteCod());
        payload.put("Sector", cliente.getSector());
        payload.put("DomicilioId", cliente.getDomicilioId());
        payload.put("Direccion", cliente.getDireccion());
        payload.put("Telefono", cliente.getTelefono());
        payload.put("TelCelular", cliente.getTelCelular());
        payload.put("eMail", cliente.getEmail());
        payload.put("LocalidadId", cliente.getLocalidadId());
        payload.put("ProvinciaId", cliente.getProvinciaId());
        payload.put("Version", cliente.getVersion());

        url = url.replace(" ", "%20");
        UtilsServices.getStreamPut(url, payload.toString());
    }

    public static ArrayList<Stock> getStockCliente(Context context, int clienteId) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + STOCK_CLIENTE, clienteId);

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String stockStr= gson.fromJson(reader, String.class);
        Stock[] stock = gson.fromJson(stockStr, Stock[].class);
        return new ArrayList<Stock>(Arrays.asList(stock));
    }

    public static ArrayList<Domicilio> getDomicilios(Context context, int clienteId) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + DOMICILIO_CLIENTES, clienteId,
                AppPreferences.getString(context, AppPreferences.KEY_REPARTIDOR, ""));

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");
        String domicilioStr= gson.fromJson(reader, String.class);
        Domicilio[] domicilios = new Gson().fromJson(domicilioStr, Domicilio[].class);

        return new ArrayList<Domicilio>(Arrays.asList(domicilios));
    }

    public static ArrayList<ClienteHistorial> getHistorialCliente(Context context, int clienteId) throws IOException {
        String url = String.format(EnviromentManager.getUrl(context) + HISTORIAL_CLIENTE, clienteId);

        InputStream in = UtilsServices.getStream(url);
        Gson gson = new Gson();

        Reader reader = new InputStreamReader(in, "UTF-8");

        String historialStr= gson.fromJson(reader, String.class);
        ClienteHistorial[] historials = gson.fromJson(historialStr, ClienteHistorial[].class);
        return new ArrayList<ClienteHistorial>(Arrays.asList(historials));
    }
}
