package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CBouix on 05/04/2017.
 */

public class Recorrido {

    @SerializedName("CabeceraId")
    private int cabeceraId;

    @SerializedName("LineaId")
    private int lineaId;

    @SerializedName("Codigo")
    private String codigo;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Orden")
    private int orden;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("ClienteCod")
    private String clienteCod;

    @SerializedName("ClienteNombre")
    private String clienteNombre;

    @SerializedName("ClienteId")
    private String clienteId;

    @SerializedName("ListaPrecioId")
    private String listaPrecioId;

    @SerializedName("ClienteDomicilio")
    private String clienteDomicilio;

    @SerializedName("ClienteDomicilioId")
    private String domicilioId;

    @SerializedName("Visitado")
    private int visitados;

    @SerializedName("VisitaPospuesta")
    private int visitaPospuesta;

    @SerializedName("SaldoActual")
    private String saldo;

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public int getCabeceraId() {
        return cabeceraId;
    }

    public int getLineaId() {
        return lineaId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getClienteDomicilio() {
        return clienteDomicilio;
    }

    public boolean isVisitados() { return this.visitados != 0; }

    public boolean isVisitaPospuesta() { return this.visitaPospuesta == 1; }

    public String getDomicilioId() {
        return domicilioId;
    }

    public void setDomicilioId(String domicilioId) {
        this.domicilioId = domicilioId;
    }

    public void setClienteDomicilio(String clienteDomicilio) {
        this.clienteDomicilio = clienteDomicilio;
    }

    public String getListaPrecioId() {
        return listaPrecioId;
    }

    public void setListaPrecioId(String listaPrecioId) {
        this.listaPrecioId = listaPrecioId;
    }
}
