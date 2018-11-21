package com.app.cbouix.sodapp.Models.Json;

import com.google.gson.annotations.SerializedName;
/**
 * Created by CBouix on 10/10/2017.
 */

public class CobranzaCab {

    @SerializedName("CabeceraId")
    public int cabeceraId;

    @SerializedName("Emision")
    public String emision;

    @SerializedName("Numero")
    public String numero;

    @SerializedName("Importe")
    public double importe;

    @SerializedName("ImporteAplicado")
    public double importeAplicado;

    @SerializedName("ClienteId")
    public int clienteId;

    @SerializedName("ClienteCod")
    public String clienteCod;

    @SerializedName("ClienteNombre")
    public String clienteNombre;

    @SerializedName("DomicilioId")
    public int domicilioId;

    @SerializedName("Direccion")
    public String direccion;

    @SerializedName("Version")
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public double getImporteAplicado() {
        return importeAplicado;
    }

    public void setImporteAplicado(double importeAplicado) {
        this.importeAplicado = importeAplicado;
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
