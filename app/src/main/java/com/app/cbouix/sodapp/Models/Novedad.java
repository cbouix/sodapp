package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 28/04/2017.
 */

public class Novedad {

    @SerializedName("ID")
    private int id;

    @SerializedName("Numero")
    private String numero;

    @SerializedName("FechaEmision")
    private String fechaEmision;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("ClienteId")
    private String clienteId;

    @SerializedName("ClienteCod")
    private String clienteCod;

    @SerializedName("ClienteNombre")
    private String clienteNombre;

    @SerializedName("DomicilioId")
    private String domicilioId;

    @SerializedName("Direccion")
    private String direccion;

    @SerializedName("RepartidorId")
    private String repartidorId;

    @SerializedName("RepartidorCod")
    private String repartidorCod;

    @SerializedName("RepartidorNombre")
    private String repartidorNombre;

    public int getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
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

    public String getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(String domicilioId) {
        this.domicilioId = domicilioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRepartidorId() {
        return repartidorId;
    }

    public void setRepartidorId(String repartidorId) {
        this.repartidorId = repartidorId;
    }

    public String getRepartidorCod() {
        return repartidorCod;
    }

    public void setRepartidorCod(String repartidorCod) {
        this.repartidorCod = repartidorCod;
    }

    public String getRepartidorNombre() {
        return repartidorNombre;
    }

    public void setRepartidorNombre(String repartidorNombre) {
        this.repartidorNombre = repartidorNombre;
    }
}
