package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 20/05/2017.
 */

public class Visita {
    @SerializedName("VisitaId")
    private int id;

    @SerializedName("FechaEmision")
    private String fecha;

    @SerializedName("Numero")
    private String numero;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("ClienteId")
    private int clienteId;

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

    @SerializedName("TipoVisitaId")
    private String tipoVisitaId;

    @SerializedName("TipoVisitaCod")
    private String tipoVisitaCod;

    @SerializedName("TipoVisitaNombre")
    private String tipoVisitaNombre;

    @SerializedName("ListaPrecioId")
    private String listPrecioId;

    public int getId() {
        return id;
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

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getTipoVisitaId() {
        return tipoVisitaId;
    }

    public void setTipoVisitaId(String tipoVisitaId) {
        this.tipoVisitaId = tipoVisitaId;
    }

    public String getTipoVisitaCod() {
        return tipoVisitaCod;
    }

    public void setTipoVisitaCod(String tipoVisitaCod) {
        this.tipoVisitaCod = tipoVisitaCod;
    }

    public String getTipoVisitaNombre() {
        return tipoVisitaNombre;
    }

    public void setTipoVisitaNombre(String tipoVisitaNombre) {
        this.tipoVisitaNombre = tipoVisitaNombre;
    }

    public String getListPrecioId() {
        return listPrecioId;
    }

    public void setListPrecioId(String listPrecioId) {
        this.listPrecioId = listPrecioId;
    }

    @Override
    public String toString() {
        return this.tipoVisitaNombre;
    }
}
