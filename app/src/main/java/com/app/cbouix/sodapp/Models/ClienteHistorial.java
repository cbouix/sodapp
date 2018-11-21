package com.app.cbouix.sodapp.Models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by Casa on 13/11/2018.
 */

public class ClienteHistorial implements Serializable {

    @SerializedName("sFechaOperacion")
    private String fecha;

    @SerializedName("sOperacionTipoCod")
    private String operacionCod;

    @SerializedName("sOperacionTipoNombre")
    private String operacion;

    @SerializedName("iOperacionId")
    private String operacionId;

    @SerializedName("sOperacionNro")
    private String operacionNro;

    @SerializedName("iClienteId")
    private String clienteId;

    @SerializedName("sClienteCod")
    private String clienteCod;

    @SerializedName("sClienteNombre")
    private String clienteNombre;

    @SerializedName("iRepartidorId")
    private String repartidorId;

    @SerializedName("sRepartidorCod")
    private String repartidorCod;

    @SerializedName("sRepartidorNombre")
    private String repartidor;

    @SerializedName("dImporte")
    private String importe;

    @SerializedName("sLeyenda")
    private String leyenda;

    @SerializedName("iDomicilioId")
    private String domicilioId;

    @SerializedName("sDireccion")
    private String direccion;

    @SerializedName("sDomicilioTipoCod")
    private String domicilioTipo;

    @SerializedName("sTelefono")
    private String telefono;

    @SerializedName("sTelCelular")
    private String celular;

    @SerializedName("sEmail")
    private String email;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOperacionCod() {
        return operacionCod;
    }

    public void setOperacionCod(String operacionCod) {
        this.operacionCod = operacionCod;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getOperacionId() {
        return operacionId;
    }

    public void setOperacionId(String operacionId) {
        this.operacionId = operacionId;
    }

    public String getOperacionNro() {
        return operacionNro;
    }

    public void setOperacionNro(String operacionNro) {
        this.operacionNro = operacionNro;
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

    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
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

    public String getDomicilioTipo() {
        return domicilioTipo;
    }

    public void setDomicilioTipo(String domicilioTipo) {
        this.domicilioTipo = domicilioTipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
