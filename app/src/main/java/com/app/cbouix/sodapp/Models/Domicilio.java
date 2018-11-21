package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 05/05/2017.
 */

public class Domicilio {

    public Domicilio(){
        this.domicilioId = 0;
        this.direccion = "--seleccione--";
    }

    @SerializedName("DomicilioId")
    private int domicilioId;

    @SerializedName("Direccion")
    private String direccion;

    @SerializedName("TipoDomicilioCod")
    private String tipoDomicilio;

    @SerializedName("LocalidadId")
    private int localidadId;

    @SerializedName("LocalidadNombre")
    private String localidadNombre;

    @SerializedName("ProvinciaId")
    private int provinciaId;

    @SerializedName("ProvinciaNombre")
    private String provinciaNombre;

    @SerializedName("ClienteId")
    private int clienteId;

    public int getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(int id){
        this.domicilioId = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoDomicilio() {
        return tipoDomicilio;
    }

    public void setTipoDomicilio(String tipoDomicilio) {
        this.tipoDomicilio = tipoDomicilio;
    }

    public int getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(int localidadId) {
        this.localidadId = localidadId;
    }

    public String getLocalidadNombre() {
        return localidadNombre;
    }

    public void setLocalidadNombre(String localidadNombre) {
        this.localidadNombre = localidadNombre;
    }

    public int getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(int provinciaId) {
        this.provinciaId = provinciaId;
    }

    public String getProvinciaNombre() {
        return provinciaNombre;
    }

    public void setProvinciaNombre(String provinciaNombre) {
        this.provinciaNombre = provinciaNombre;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return this.direccion;
    }
}
