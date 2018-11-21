package com.app.cbouix.sodapp.Models.Json;

import com.google.gson.annotations.SerializedName;
/**
 * Created by CBouix on 10/10/2017.
 */

public class RemitoCab {

    @SerializedName("CabeceraId")
    private int cabeceraId;

    @SerializedName("Emision")
    private String emision;

    @SerializedName("Numero")
    private String numero;

    @SerializedName("Importe")
    private double importe;

    @SerializedName("ClienteId")
    private int clienteId;

    @SerializedName("ClienteCod")
    private String clienteCod;

    @SerializedName("ClienteNombre")
    private String clienteNombre;

    @SerializedName("DomicilioId")
    private int domicilioId;

    @SerializedName("Direccion")
    private String direccion;

    @SerializedName("ListaPrecioId")
    private int listPrecioId;

    @SerializedName("Version")
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getListPrecioId() {
        return listPrecioId;
    }

    public void setListPrecioId(int listPrecioId) {
        this.listPrecioId = listPrecioId;
    }

    public int getCabeceraId() {
        return cabeceraId;
    }

    public void setCabeceraId(int cabeceraId) {
        this.cabeceraId = cabeceraId;
    }

    public String getEmision() {
        return emision;
    }

    public void setEmision(String emision) {
        this.emision = emision;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteCod() {
        return clienteCod;
    }

    public void setClienteCod(String clienteCod) {
        this.clienteCod = clienteCod;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public int getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(int domicilioId) {
        this.domicilioId = domicilioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
